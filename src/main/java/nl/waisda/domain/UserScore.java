package nl.waisda.domain;


public class UserScore implements Comparable<UserScore> {

	private User user;
	private int score;
	private int position;
	private int countTags;
	private int countMatches;

	public UserScore(User user) {
		this.user = user;
	}

	public UserScore(User user, int score) {
		this.user = user;
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int gameScore) {
		this.score = gameScore;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCountTags() {
		return countTags;
	}

	public void setCountTags(int countTags) {
		this.countTags = countTags;
	}

	public int getCountMatches() {
		return countMatches;
	}

	public void setCountMatches(int countMatches) {
		this.countMatches = countMatches;
	}

	public void count(TagEntry tag) {
		score += tag.getScore();
		countTags++;
		if (tag.getMatchingTagEntry() != null) {
			countMatches++;
		}
	}

	@Override
	public int compareTo(UserScore other) {
		return other.score - this.score;
	}

}
