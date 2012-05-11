package nl.waisda.model;

import java.util.List;

import nl.waisda.domain.Video;


public class VideoStats {

	private Video video;
	private List<String> topTags;

	public VideoStats(Video video, List<String> topTags) {
		this.video = video;
		this.topTags = topTags;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public List<String> getTopTags() {
		return topTags;
	}

	public void setTopTags(List<String> topTags) {
		this.topTags = topTags;
	}

}
