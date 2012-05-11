package nl.waisda.model;

import java.util.List;

import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;



public class Profile {

	private User user;
	private List<TagEntry> pioneerMatches;
	private List<UserScore> ranking;
	private List<GameScore> recentGames;

	public Profile(User user, List<TagEntry> pioneerMatches,
			List<UserScore> ranking, List<GameScore> recentGames) {
		this.user = user;
		this.pioneerMatches = pioneerMatches;
		this.ranking = ranking;
		this.recentGames = recentGames;
	}

	public User getUser() {
		return user;
	}

	public List<TagEntry> getPioneerMatches() {
		return pioneerMatches;
	}

	public List<UserScore> getRanking() {
		return ranking;
	}

	public List<GameScore> getRecentGames() {
		return recentGames;
	}

}
