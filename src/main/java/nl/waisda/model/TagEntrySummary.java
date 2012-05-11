package nl.waisda.model;

import java.util.List;

import nl.waisda.domain.TagEntry;


/**
 * Summary of a game, containing various statistics on the numbers of tags
 * entered.
 */
public class TagEntrySummary {

	/** The total number of tags. */
	private int countTags;

	/** The number of tags that did not match in any way. */
	private int countEmptyTags;

	/** The number of tags that matched another player's tag. */
	private int countMatchingTags;

	/** The number of tags that matched another player's tag and were pioneers. */
	private int countPioneerTags;

	/** The number of dictionary matches, for each dictionary type. */
	private int countDictionaryMatches;

	private TagEntrySummary() {
	}

	/** Creates a tag entry summary from a list of tag entries. */
	public static TagEntrySummary fromEntries(List<TagEntry> entries) {
		TagEntrySummary summary = new TagEntrySummary();
		for (TagEntry tag : entries) {
			summary.countTags++;
			if (tag.getMatchingTagEntry() != null) {
				summary.countMatchingTags++;
				if (tag.isPioneer()) {
					summary.countPioneerTags++;
				}
			}

			String t = tag.getDictionary();
			if (t != null) {
				summary.countDictionaryMatches++;
			}
			if (t == null && tag.getMatchingTagEntry() == null) {
				summary.countEmptyTags++;
			}
		}
		return summary;
	}

	public int getCountTags() {
		return countTags;
	}

	public int getCountEmptyTags() {
		return countEmptyTags;
	}

	public int getCountMatchingTags() {
		return countMatchingTags;
	}

	public int getCountPioneerTags() {
		return countPioneerTags;
	}

	public int getCountDictionaryMatches() {
		return countDictionaryMatches;
	}

}
