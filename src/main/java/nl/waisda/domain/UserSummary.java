package nl.waisda.domain;

import java.util.Comparator;

public class UserSummary {

	public static final UserSummary GHOST = new UserSummary(0, "Previous players", 0,
			"http://www.gravatar.com/avatar/0?d=mm&s=30");

	private int id;
	private String name;
	private int gameScore;
	private String smallAvatarUrl;

	public UserSummary(int id, String name, int gameScore, String smallAvatarUrl) {
		this.id = id;
		this.name = name;
		this.gameScore = gameScore;
		this.smallAvatarUrl = smallAvatarUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	public String getSmallAvatarUrl() {
		return smallAvatarUrl;
	}

	public void setSmallAvatarUrl(String smallAvatarUrl) {
		this.smallAvatarUrl = smallAvatarUrl;
	}

	/**
	 * Sorteert van grote scores naar kleine scores.
	 */
	public static Comparator<UserSummary> COMPARE_BY_GAME_SCORE = new Comparator<UserSummary>() {

		@Override
		public int compare(UserSummary x, UserSummary y) {
			return y.gameScore - x.gameScore;
		}
	};

}
