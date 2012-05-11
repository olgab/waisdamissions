package nl.waisda.interceptors;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.waisda.controllers.StaticController;
import nl.waisda.domain.User;
import nl.waisda.model.GameScore;
import nl.waisda.repositories.GameRepository;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.services.GameService;
import nl.waisda.services.ScoringService;
import nl.waisda.services.UserSessionService;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class GlobalModelInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private ScoringService scoringService;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private GameService gameService;

	@Autowired
	private TagEntryRepository tagRepo;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (!(handler instanceof StaticController) && modelAndView != null) {
			User currentUser = userSessionService.getCurrentUser(request
					.getSession());

			if (currentUser != null) {
				modelAndView.addObject("user", currentUser);

				ObjectMapper om = new ObjectMapper();
				StringWriter sw = new StringWriter();
				om.writeValue(sw, currentUser);
				modelAndView.addObject("userJson", sw);

				int countNewPioneerMatches = 0;
				GameScore lastGamePlayed = gameRepo.getLastGamePlayed(currentUser
						.getId());
				if (lastGamePlayed != null) {
					countNewPioneerMatches = tagRepo.countPioneerMatchesSince(
							currentUser.getId(), lastGamePlayed.getGame().getEnd());
				}
				currentUser.setCountNewPioneerMatches(countNewPioneerMatches);
			}

			modelAndView.addObject("globalStats",
					scoringService.getGlobalStats());
			modelAndView.addObject("currentQueues", gameService.getCurrentQueues());
		}
	}
}
