package nl.waisda.model;

import nl.waisda.domain.Video;

public class Channel {

	private Video video;
	private int highscore;

	public Channel(Video video, int highscore) {
		super();
		this.video = video;
		this.highscore = highscore;
	}

	public Video getVideo() {
		return video;
	}

	public int getHighscore() {
		return highscore;
	}

}
