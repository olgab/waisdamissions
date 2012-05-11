package nl.waisda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;



public class TopScores {

	private ArrayList<UserScore> topscores;

	private Map<Integer, Integer> userToPos;

	/** Demand an ArrayList for fast random access. */
	public TopScores(ArrayList<UserScore> scores) {
		this.topscores = scores;
		userToPos = new TreeMap<Integer, Integer>();
		int i = 0;
		for (UserScore u : scores) {
			userToPos.put(u.getUser().getId(), i);
			i++;
		}
	}

	public List<UserScore> getTopTen() {
		int hi = Math.min(size(), 10);
		return topscores.subList(0, hi);
	}

	/** Returns the user's position, or null if not present. */
	public Integer getPosition(User user) {
		return userToPos.get(user.getId());
	}

	public int size() {
		return topscores.size();
	}

	/**
	 * Returns the direct context for this user in this scoreboard, or null if
	 * the given user is not present.
	 */
	public List<UserScore> getContext(User user) {
		Integer pos = getPosition(user);
		if (pos == null) {
			return null;
		} else {
			int lo = Math.max(0, pos - 4); // inclusive
			int hi = Math.min(size(), pos + 5); // exclusive
			return topscores.subList(lo, hi);
		}
	}

}
