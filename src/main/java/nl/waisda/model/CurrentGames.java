package nl.waisda.model;

import java.util.List;

import nl.waisda.domain.Game;



public class CurrentGames {

	private final long serverTime;
	private final List<Game> games;

	public CurrentGames(long now, List<Game> games) {
		this.serverTime = now;
		this.games = games;
	}

	public long getServerTime() {
		return serverTime;
	}

	public List<Game> getGames() {
		return games;
	}

}
