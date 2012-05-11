package nl.waisda.services;

import java.security.SecureRandom;
import java.util.List;

import nl.waisda.domain.ResetPassword;
import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;
import nl.waisda.model.GameScore;
import nl.waisda.model.Profile;
import nl.waisda.repositories.GameRepository;
import nl.waisda.repositories.ResetPasswordRepository;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.repositories.UserRepository;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TagEntryRepository tagRepo;

	@Autowired
	private ScoringService scoringService;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private ResetPasswordRepository rpwRepo;

	public Profile getProfile(int userId) {
		User user = userRepo.getById(userId);

		if (user == null || user.isAnonymous()) {
			return null;
		}

		List<TagEntry> pioneerMatches = tagRepo.getLastPioneerMatches(userId,
				10);
		List<UserScore> ranking = scoringService.getGlobalStats()
				.getTopScores().getContext(user);
		List<GameScore> recentGames = gameRepo.getRecentGames(userId, 10);
		return new Profile(user, pioneerMatches, ranking, recentGames);
	}

	public ResetPassword requestPasswordReset(User user) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[64];
		random.nextBytes(bytes);

		String plainTextKey = Base64.encodeBase64String(bytes);
		plainTextKey = plainTextKey.replaceAll("[^a-zA-Z0-9]", "");
		ResetPassword reset = new ResetPassword(user, plainTextKey);
		rpwRepo.store(reset);

		return reset;
	}

}
