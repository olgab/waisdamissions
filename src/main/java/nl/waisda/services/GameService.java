package nl.waisda.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.waisda.domain.Game;
import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;
import nl.waisda.domain.Video;
import nl.waisda.exceptions.NotFoundException;
import nl.waisda.model.Cache;
import nl.waisda.model.Recap;
import nl.waisda.model.TagEntrySummary;
import nl.waisda.model.Value;
import nl.waisda.repositories.GameRepository;
import nl.waisda.repositories.TagEntryRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GameService {

	private Logger log = Logger.getLogger(GameService.class);

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private TagEntryRepository tagRepo;

	private Cache<List<Game>> currentGamesCache;

	{
		Value<List<Game>> fetchCurrentGames = new Value<List<Game>>() {

			@Override
			public List<Game> get() {
				Date now = new Date();
				return gameRepo.getQueuesFor(now);
			}
		};
		currentGamesCache = new Cache<List<Game>>(fetchCurrentGames, 500);
	}

	@Transactional
	public Game createGame(User user, Video video) throws NotFoundException {
		if (!video.isEnabled()) {
			log.info(String.format("Ignoring request for new game for disabled video %d", video.getId()));
			throw new NotFoundException();
		}

		List<Game> currentGames = gameRepo.getQueuesFor(new Date(), video);

		if (currentGames.isEmpty()) {
			Game game = new Game();

			game.setInitiator(user);

			Calendar c = Calendar.getInstance();
			c.add(Calendar.MILLISECOND, Game.QUEUE_TIME_MS);
			Date start = c.getTime();

			game.setStart(start);
			game.setVideo(video);
			game.setCountExistingVideoTags(tagRepo.countTags(video.getId()));

			gameRepo.store(game);
			currentGamesCache.invalidate();

			log.info(String.format(
					"Created new game %d for video %d and user %d",
					game.getId(), video.getId(), user.getId()));

			return game;
		} else {
			Game game = currentGames.get(0);
			log.info(String.format(
					"Returning existing game %d for video %d and user %d",
					game.getId(), video.getId(), user.getId()));
			return game;
		}
	}

	public Game getGameById(int gameId) {
		return gameRepo.getById(gameId);
	}

	public List<Game> getCurrentQueues() {
		return currentGamesCache.get();
	}

	public Recap getRecap(Game game, User owner) {
		// Prepare all the information needed for the Recap.
		List<TagEntry> entries = tagRepo.getEntries(game.getId());
		List<TagEntry> ownerEntries = new ArrayList<TagEntry>(entries.size());
		Map<User, UserScore> scores = new TreeMap<User, UserScore>();
		int ownerScore = 0;

		for (TagEntry tag : entries) {
			User u = tag.getOwner();

			if (u.getId() == owner.getId()) {
				ownerEntries.add(tag);
				ownerScore += tag.getScore();
			}

			UserScore score = scores.get(u);
			if (score == null) {
				score = new UserScore(u);
				scores.put(u, score);
			}
			score.count(tag);
		}

		ArrayList<UserScore> participants = new ArrayList<UserScore>(
				scores.values());
		Collections.sort(participants);

		int ownerPosition = 0;
		for (int i = 0; i < participants.size(); i++) {
			UserScore participant = participants.get(i);
			participant.setPosition(i);
			if (participant.getUser().getId() == owner.getId()) {
				ownerPosition = i;
			}
		}

		Recap recap = new Recap();
		recap.setGame(game);
		recap.setOwner(owner);
		recap.setOwnerScore(ownerScore);
		recap.setParticipants(participants);
		recap.setOwnerPosition(ownerPosition);
		recap.setTagEntries(ownerEntries);
		recap.setSummary(TagEntrySummary.fromEntries(ownerEntries));
		return recap;
	}

}