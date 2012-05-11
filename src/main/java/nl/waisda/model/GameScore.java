package nl.waisda.model;

import nl.waisda.domain.Game;

public class GameScore {

	private Game game;
	private int score;
	private int countTags;

	public GameScore(Game game, int score, int countTags) {
		this.game = game;
		this.score = score;
		this.countTags = countTags;
	}

	public Game getGame() {
		return game;
	}

	public int getScore() {
		return score;
	}

	public int getCountTags() {
		return countTags;
	}

}
