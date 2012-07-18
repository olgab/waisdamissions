package nl.waisda.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.waisda.domain.User;
import nl.waisda.domain.Video;
import nl.waisda.domain.Game;

import nl.waisda.exceptions.NotFoundException;

import nl.waisda.model.Channel;
import nl.waisda.repositories.VideoRepository;
import nl.waisda.services.UserSessionService;
import nl.waisda.services.VideoService;
import nl.waisda.services.GameService;
import nl.waisda.services.UserSessionService;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MissionController {

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private VideoRepository videoRepo;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private VideoService videoService;

	@RequestMapping(value = { "/mission/home/{missionID}"})
	public String missionHome(@PathVariable Long missionID, @RequestParam(defaultValue = "1", required = false) int page, ModelMap model, HttpSession session) {
		List<Channel> channels = videoService.getChannelContent();
		model.addAttribute("channels", channels);
		model.addAttribute("cssClass", "home");
		model.addAttribute("mission", missionID);
		
		return "home";
	}

	@RequestMapping("/mission/{missionID}/start-game/{videoId}")
	public String startGame(@PathVariable int videoId, ModelMap model, HttpSession session)
			throws NotFoundException {
		User user = userSessionService.requireCurrentUserOrCreateAnonymous(session);
		Video video = videoRepo.getById(videoId);
		Game game = gameService.createGame(user, video);
		
		//missionService.storeGame(missionID, game.getId());

		return "redirect:/game/" + game.getId();
	}

}
