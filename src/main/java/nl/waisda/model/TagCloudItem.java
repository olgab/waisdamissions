package nl.waisda.model;

public class TagCloudItem {

	private String normalizedTag;
	private int relativeSize; // 1-5

	public TagCloudItem(String normalizedTag, int relativeSize) {
		this.normalizedTag = normalizedTag;
		this.relativeSize = relativeSize;
	}

	public String getNormalizedTag() {
		return normalizedTag;
	}

	public int getRelativeSize() {
		return relativeSize;
	}

}
