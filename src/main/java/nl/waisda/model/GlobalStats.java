package nl.waisda.model;

import java.util.List;

public class GlobalStats {


	private final int totalTags;
	private final int totalMatches;
	private List<TagCloudItem> tagCloud;
	private TopScores topScores;
	private final int currentlyPlaying;

	public GlobalStats(int totalTags, int totalMatches,
			List<TagCloudItem> tagCloud, TopScores topScores,
			int currentlyPlaying) {
		this.totalTags = totalTags;
		this.totalMatches = totalMatches;
		this.tagCloud = tagCloud;
		this.topScores = topScores;
		this.currentlyPlaying = currentlyPlaying;
	}

	public int getTotalTags() {
		return totalTags;
	}

	public int getTotalMatches() {
		return totalMatches;
	}

	public static int round(int n) {
		if (n < 100) {
			return n;
		} else if (n < 1000) {
			return n / 10 * 10;
		} else if (n < 10000) {
			return n / 100 * 100;
		} else {
			return n / 1000 * 1000;
		}
	}

	public List<TagCloudItem> getTagCloud() {
		return tagCloud;
	}

	public TopScores getTopScores() {
		return topScores;
	}

	public int getCurrentlyPlaying() {
		return currentlyPlaying;
	}

}
