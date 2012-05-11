package nl.waisda.model;

import java.util.ArrayList;
import java.util.List;

import nl.waisda.domain.TagEntry;


public class TagEntryStats {

	private String normalizedTag;
	private TagEntry firstEntry;
	private List<VideoStats> videoStats = new ArrayList<VideoStats>();

	public String getNormalizedTag() {
		return normalizedTag;
	}

	public void setNormalizedTag(String normalizedTag) {
		this.normalizedTag = normalizedTag;
	}

	public TagEntry getFirstEntry() {
		return firstEntry;
	}

	public void setFirstEntry(TagEntry firstEntry) {
		this.firstEntry = firstEntry;
	}

	public List<VideoStats> getVideoStats() {
		return videoStats;
	}

	public void setVideoStats(List<VideoStats> videoStats) {
		this.videoStats = videoStats;
	}

}
