package nl.waisda.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.waisda.domain.Game;
import nl.waisda.domain.Participant;
import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;
import nl.waisda.domain.UserSummary;
import nl.waisda.domain.Video;
import nl.waisda.exceptions.Forbidden;
import nl.waisda.exceptions.NotFoundException;
import nl.waisda.model.CurrentGames;
import nl.waisda.model.GameUpdate;
import nl.waisda.model.Recap;
import nl.waisda.model.ShallowTagEntry;
import nl.waisda.repositories.ParticipantRepository;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.repositories.UserRepository;
import nl.waisda.repositories.VideoRepository;
import nl.waisda.services.GameService;
import nl.waisda.services.ScoringService;
import nl.waisda.services.UserSessionService;
import nl.waisda.validators.RegisterValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("userSession")
public class GameController {
	
	private Logger log = Logger.getLogger(GameController.class);

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private VideoRepository videoRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private TagEntryRepository tagEntryRepo;
	
	@Autowired
	private ParticipantRepository participantRepo;

	@Autowired
	private ScoringService scoringService;
	
	@RequestMapping("/start-game/{videoId}")
	public String startGame(@PathVariable int videoId, ModelMap model, HttpSession session)
			throws NotFoundException {
		User user = userSessionService.requireCurrentUserOrCreateAnonymous(session);
		Video video = videoRepo.getById(videoId);
		Game game = gameService.createGame(user, video);

		return "redirect:/game/" + game.getId();
	}
	
	@RequestMapping("/game/{gameId}")
	public String game(@PathVariable int gameId, ModelMap model, HttpSession session)
			throws NotFoundException {
		Game game = gameService.getGameById(gameId);
		
		if (game == null) {
			throw new NotFoundException("Unknown game " + gameId);
		} else if (game.hasEnded()) {
			log.info(String.format("Redirecting request for old game %d to /", gameId));
			return "redirect:/";
		}
		
		User user = userSessionService.requireCurrentUserOrCreateAnonymous(session);

		if (participantRepo.get(user.getId(), game.getId()) == null) {
			participantRepo.store(new Participant(user, game));
			log.info(String.format("%s joins game %d", user.getShortDescription(), gameId));
		}

		model.addAttribute("game", game);
		model.addAttribute("cssClass", "game");
		
		return "game";
	}
	
	@RequestMapping("/game/{gameId}/update/{time}")
	@ResponseBody
	public GameUpdate update(@PathVariable int gameId, @PathVariable int time,
			ModelMap model, HttpSession session) throws NotFoundException {
		// Fetch data
		User user = userSessionService.requireCurrentUser(session);
		Game game = gameService.getGameById(gameId);

		if (game == null) {
			throw new NotFoundException("Unknown game " + gameId);
		}

		List<UserScore> participants = tagEntryRepo.getParticipants(game
				.getId());
		List<TagEntry> myEntries = tagEntryRepo.getEntries(
				game.getId(), user.getId());
		
		GameUpdate update = new GameUpdate();
		update.setOwnId(user.getId());

		int gameScore = 0;
		for (TagEntry te : myEntries) {
			gameScore += te.getScore();
		}
		update.setGameScore(gameScore);

		for (TagEntry tag : myEntries) {
			update.getTagEntries().add(ShallowTagEntry.fromTagEntry(tag));
		}

		List<UserSummary> summaries = new ArrayList<UserSummary>();
		for (UserScore sgs : participants) {
			User s = sgs.getUser();
			summaries.add(new UserSummary(s.getId(), s.getName(), sgs.getScore(), s.getSmallAvatarUrl()));
		}
		Collections.sort(summaries, UserSummary.COMPARE_BY_GAME_SCORE);

		if (game.getCountExistingVideoTags() > 0) {
			summaries.add(UserSummary.GHOST);
		}

		update.setStudents(summaries);

		return update;
	}
	
	@RequestMapping("/current-queues")
	@ResponseBody
	public CurrentGames currentQueues(ModelMap model) {
		return new CurrentGames(new Date().getTime(), gameService.getCurrentQueues());
	}

	@RequestMapping("/tag-entry")
	@ResponseBody
	@Transactional
	public ShallowTagEntry enterTag(@ModelAttribute TagEntry tagEntry,
			HttpSession session) {
		User user = userSessionService.getCurrentUser(session);

		if (user == null) {
			return null;
		}

		tagEntry.setTag(RegisterValidator.normalizeWhitespace(tagEntry.getTag()));
		tagEntry.setOwner(user);
		tagEntry.setNormalizedTag();

		if (tagEntry.getNormalizedTag().length() == 0) {
			return null;
		}

		Game game = gameService.getGameById(tagEntry.getGame().getId());
		tagEntry.setGame(game);

		if (game.acceptsNewTagEntryAt(tagEntry.getGameTime()) && tagEntry.getNormalizedTag().length() < 40) {
			if (tagEntryRepo.alreadyEntered(tagEntry.getGame().getId(),
					tagEntry.getNormalizedTag(), tagEntry.getGameTime(),
					user.getId())) {
				log.info(String
						.format("Ignoring duplicate entry %s for game %d, user %d, time %d",
								tagEntry.getNormalizedTag(), tagEntry.getGame()
										.getId(), user.getId(), tagEntry
										.getGameTime()));
				return null;
			}
		} else {
			log.info(String
					.format("Ignoring tag %s for game %d, user %d, time %d (actual game time %d, delay %d)",
							tagEntry.getTag(), tagEntry.getGame().getId(),
							user.getId(), tagEntry.getGameTime(),
							game.getElapsed(),
							game.getElapsed() - tagEntry.getGameTime()));
			return null;
		}

		scoringService.updateDictionary(tagEntry);
		scoringService.updateMatchAndStore(tagEntry, true);

		log.info(String
				.format("Registering tag %s #%d with score %d for game %d, user %d, time %d (actual game time %d, delay %d)",
						tagEntry.getTag(), tagEntry.getId(), tagEntry.getScore(), tagEntry.getGame()
								.getId(), user.getId(), tagEntry.getGameTime(),
						game.getElapsed(),
						game.getElapsed() - tagEntry.getGameTime()));

		return ShallowTagEntry.fromTagEntry(tagEntry);
	}

	@RequestMapping("/game/{gameId}/recap/{ownerId}")
	public String recap(@PathVariable int gameId, @PathVariable int ownerId,
			ModelMap model, HttpSession session, HttpServletRequest request) throws NotFoundException,
			Forbidden {
		
		User user = userSessionService.getCurrentUser(session);

		if (user == null) {
			return "redirect:/inloggen";
		} else if (user.getId() != ownerId) {
			throw new Forbidden();
		}
		
		User owner = userRepo.getById(ownerId);
		Game game = gameService.getGameById(gameId);
		Recap recap = gameService.getRecap(game, owner);
		if (recap.isEmpty()) {
			log.info(String.format("Redirecting request for empty recap "
					+ "for game %d, user %d to / (referrer: %s)", gameId,
					ownerId, request.getHeader("Referer")));
			return "redirect:/";
		} else {
			model.put("recap", recap);
			return "recap";
		}
	}

	@RequestMapping("/error")
	public void error() {
		throw new RuntimeException();
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

}
