package nl.waisda.model;

import java.util.List;

import nl.waisda.domain.Game;
import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;



public class Recap {

	private Game game;

	private User owner;

	private int ownerScore;

	private int ownerPosition;

	private List<TagEntry> tagEntries;

	private TagEntrySummary summary;

	private List<UserScore> participants;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getOwnerScore() {
		return ownerScore;
	}

	public void setOwnerScore(int ownerScore) {
		this.ownerScore = ownerScore;
	}

	public int getOwnerPosition() {
		return ownerPosition;
	}

	public void setOwnerPosition(int ownerPosition) {
		this.ownerPosition = ownerPosition;
	}

	public List<TagEntry> getTagEntries() {
		return tagEntries;
	}

	public void setTagEntries(List<TagEntry> tagEntries) {
		this.tagEntries = tagEntries;
	}

	public TagEntrySummary getSummary() {
		return summary;
	}

	public void setSummary(TagEntrySummary summary) {
		this.summary = summary;
	}

	public List<UserScore> getParticipants() {
		return participants;
	}

	public void setParticipants(List<UserScore> participants) {
		this.participants = participants;
	}

	public boolean isEmpty() {
		return this.tagEntries.isEmpty();
	}

}
