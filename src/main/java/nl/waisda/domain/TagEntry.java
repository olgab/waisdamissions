package nl.waisda.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.waisda.model.Util;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Index;

@Entity
public class TagEntry implements Serializable {

	private static final long serialVersionUID = -3032438049433062208L;

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"EEEE d MMMM yyyy", Util.DUTCH_LOCALE);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/*
	 * Fields: input from User
	 */

	@Basic(optional = false)
	private String tag;

	@Basic(optional = false)
	private String normalizedTag;

	/** Time within game. */
	@Basic(optional = false)
	private int gameTime;

	@Basic(optional = false)
	private int typingDuration;

	@ManyToOne(optional = false)
	private Game game;

	@ManyToOne(optional = false)
	private User owner;

	@Basic(optional = false)
	@Index(name = "creationDate")
	private Date creationDate = new Date();

	/*
	 * Fields: derived from score-engine
	 */

	@Basic(optional = true)
	private String dictionary;

	@ManyToOne(fetch = FetchType.EAGER)
	private TagEntry matchingTagEntry;

	@Column(nullable = false, columnDefinition = "BOOLEAN")
	private boolean pioneer;

	@Basic(optional = false)
	private int score;

	/*
	 * Business logic
	 */

	public static List<String> mapTag(List<TagEntry> entries) {
		List<String> tags = new ArrayList<String>();
		for (TagEntry entry : entries) {
			tags.add(entry.getTag());
		}
		return tags;
	}

	public String getFriendlyTime() {
		return getFriendlyTime(gameTime);
	}

	public static String getFriendlyTime(int time) {
		int s = time / 1000;
		int m = s / 60;
		s %= 60;
		return String.format("%d:%02d", m, s);
	}

	/** Recomputes the score from the current match and bonus fields. */
	public int recomputeScore() {
		if (tag.length() < 2) {
			return 0;
		}

		if (matchingTagEntry != null
				&& matchingTagEntry.getOwner().getId() == getOwner().getId()) {
			// Literal matches with owner's own tags award no points. Return immediately.
			return 0;
		}

		int score = 0;

		if (dictionary != null) {
			// Matches with a dictionary award 25 points. If you'd like the
			// score to depend on the specific dictionary, you can do that here.
			score += 25;
		}

		if (matchingTagEntry != null) {
			score += 50;

			if (pioneer) {
				// Pioneer only matters if a matching tag entry was found.
				score += 100;
			}
		}

		if (score == 0) {
			// Players get 5 points for the effort, even if their entry didn't match.
			score = 5;
		}

		return score;
	}

	/** Updates this entry's score field using recomputeScore. */
	public void updateScore() {
		setScore(recomputeScore());
	}

	public boolean isOriginal() {
		return matchingTagEntry == null
				|| matchingTagEntry.getOwner().getId() != getOwner().getId();
	}

	public static String normalize(String name) {
		name = name.toLowerCase();

		// Remove parenthesized parts
		// E.g. Laren (Gelderland) -> Laren
		name = name.replaceAll("\\([^\\)]*\\)", "");

		// Remove diacritics.
		name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll(
				"[^a-z0-9]+", "");

		return name;
	}

	public void setNormalizedTag() {
		setNormalizedTag(TagEntry.normalize(tag));
	}

	/*
	 * Getters and setters
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNormalizedTag() {
		return normalizedTag;
	}

	public void setNormalizedTag(String normalizedTag) {
		this.normalizedTag = normalizedTag;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getTypingDuration() {
		return typingDuration;
	}

	public void setTypingDuration(int typingDuration) {
		this.typingDuration = typingDuration;
	}

	@JsonIgnore
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@JsonIgnore
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getPrettyCreationDate() {
		return DATE_FORMAT.format(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@JsonIgnore
	public TagEntry getMatchingTagEntry() {
		return matchingTagEntry;
	}

	public void setMatchingTagEntry(TagEntry matchingTagEntry) {
		this.matchingTagEntry = matchingTagEntry;
	}

	public boolean isPioneer() {
		return pioneer;
	}

	public void setPioneer(boolean pioneer) {
		this.pioneer = pioneer;
	}

	public String toString() {
		return String.format(
				"#%d normalizedTag:%s user:%d video:%d game:%d time:%d",
				getId(), getNormalizedTag(), getOwner().getId(), getGame()
						.getVideo().getId(), getGame().getId(), getGameTime());
	}

}