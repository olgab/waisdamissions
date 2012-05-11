package nl.waisda.services;

import javax.servlet.http.HttpSession;

import nl.waisda.domain.User;
import nl.waisda.exceptions.Forbidden;
import nl.waisda.repositories.GameRepository;
import nl.waisda.repositories.ParticipantRepository;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.repositories.UserRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserSessionService {

	private Logger log = Logger.getLogger(UserSessionService.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TagEntryRepository tagEntryRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private ParticipantRepository participantRepo;

	public User getCurrentUser(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userSession");
		if (userId != null) {
			User user = userRepo.getById(userId);
			if (user == null) {
				// User no longer exists.
				logout(session);
			}
			return user;
		} else {
			return null;
		}
	}

	public User requireCurrentUser(HttpSession session) {
		User u = getCurrentUser(session);
		if (u != null) {
			return u;
		} else {
			throw new Forbidden("User should be logged in.");
		}
	}

	public User requireCurrentUserOrCreateAnonymous(HttpSession session) {
		checkNotNew(session);

		User user = getCurrentUser(session);

		if (user == null) {
			user = new User();
			user.setCreationDate();
			userRepo.store(user);
			login(session, user);
		}

		return user;
	}

	public void login(HttpSession session, User newUser) {
		checkNotNew(session);
		User oldUser = getCurrentUser(session);
		if (oldUser != null && oldUser.isAnonymous() && !newUser.isAnonymous()) {
			mergeUsers(oldUser, newUser);
		}
		session.setAttribute("userSession", newUser.getId());
	}

	public void logout(HttpSession session) {
		session.removeAttribute("userSession");
	}

	private void checkNotNew(HttpSession session) {
		// Een gebruiker mag niet inloggen als-ie cookies uit heeft staan.
		// Dit voorkomt dat we onnodig anonieme gebruikers aanmaken voor
		// gebruikers zonder cookies.
		if (session.isNew()) {
			throw new IllegalStateException(
					"Cannot authenticate a new session.");
		}
	}

	@Transactional
	public void mergeUsers(User source, User target) {
		if (!source.isAnonymous()) {
			throw new IllegalArgumentException("Source user must be anonymous.");
		}
		if (target.isAnonymous()) {
			throw new IllegalArgumentException(
					"Target user must not be anonymous.");
		}
		log.info(String.format("Merging anonymous user %d "
				+ "with existing user %d", source.getId(), target.getId()));
		gameRepo.moveGames(source, target);
		tagEntryRepo.moveTagEntries(source, target);
		participantRepo.moveParticipants(source, target);
	}

}
