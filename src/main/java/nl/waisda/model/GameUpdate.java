package nl.waisda.model;

import java.util.ArrayList;
import java.util.List;

import nl.waisda.domain.UserSummary;



public class GameUpdate {

	/**
	 * The user id of the currently logged in user. Included here because the
	 * user might (re)authenticate during gameplay.
	 */
	private int ownId;

	/**
	 * The all time score of the user.
	 */
	private int totalScore;

	/**
	 * The score of the user in the current game.
	 */
	private int gameScore;

	/**
	 * Contains the TagEntries of the current user for this game.
	 */
	private List<ShallowTagEntry> tagEntries = new ArrayList<ShallowTagEntry>();

	/**
	 * (Shallow) summaries of fellow users' states during the game.
	 */
	private List<UserSummary> students;

	public int getOwnId() {
		return ownId;
	}

	public void setOwnId(int ownId) {
		this.ownId = ownId;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	public List<ShallowTagEntry> getTagEntries() {
		return tagEntries;
	}

	public void setTagEntries(List<ShallowTagEntry> tagEntries) {
		this.tagEntries = tagEntries;
	}

	public List<UserSummary> getStudents() {
		return students;
	}

	public void setStudents(List<UserSummary> students) {
		this.students = students;
	}

}
