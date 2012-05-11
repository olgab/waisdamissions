package nl.waisda.model;

import nl.waisda.domain.TagEntry;

/** Meant for JSON generation during game updates. */
public class ShallowTagEntry {

	private int id;
	private String tag;
	private int score;
	private String dictionary;
	private boolean pioneer;
	private int gameTime;
	private String matchingTag;
	private String matchingTagOwnerName;

	public ShallowTagEntry(int id, String tag, int score, String dictionary,
			boolean pioneer, int gameTime, String matchingTag,
			String matchingTagOwnerName) {
		this.id = id;
		this.tag = tag;
		this.score = score;
		this.dictionary = dictionary;
		this.pioneer = pioneer;
		this.gameTime = gameTime;
		this.matchingTag = matchingTag;
		this.matchingTagOwnerName = matchingTagOwnerName;
	}

	public static ShallowTagEntry fromTagEntry(TagEntry tag) {
		TagEntry match = tag.getMatchingTagEntry();
		ShallowTagEntry shallow = new ShallowTagEntry(tag.getId(),
				tag.getTag(), tag.getScore(), tag.getDictionary(),
				tag.isPioneer(), tag.getGameTime(),
				match != null ? match.getTag() : null, match != null ? match
						.getOwner().getName() : null);
		return shallow;
	}

	public int getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public int getScore() {
		return score;
	}

	public String getDictionary() {
		return dictionary;
	}

	public boolean isPioneer() {
		return pioneer;
	}

	public int getGameTime() {
		return gameTime;
	}

	public String getMatchingTag() {
		return matchingTag;
	}

	public String getMatchingTagOwner() {
		return matchingTagOwnerName;
	}
}
