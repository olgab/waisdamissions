package nl.waisda.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;

@Entity
public class Video implements Comparable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Basic
	private String title;

	/** Duration in ms. */
	@Column(nullable = false)
	private int duration;

	@Basic
	private String imageUrl;

	@Basic
	private boolean enabled;

	@Formula("(SELECT COUNT(*) FROM Game g WHERE g.video_id = id)")
	private int timesPlayed;
	
	@Formula("(SELECT COUNT(g.video_id) FROM TagEntry te JOIN Game g ON te.game_id = g.id WHERE g.video_id = id AND te.matchingTagEntry_id IS NOT NULL)")
	private int numberOfTags;

	@Enumerated(EnumType.STRING)
	private PlayerType playerType;

	@Basic
	private String fragmentID;

	/** Start time within episode, in ms. */
	@Basic
	private Integer startTime;

	/** Fragmentenrubriek zoals in MBH dump. */
	private Integer sectionNid;

	private String sourceUrl;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getDuration() {
		return duration;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public String getFragmentID() {
		return fragmentID;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getSectionNid() {
		return sectionNid;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public String getPrettyDuration() {
		return TagEntry.getFriendlyTime(duration);
	}
	
	public boolean isTagComplete() {
		return numberOfTags >= duration / 20000;
	}

	@Override
	public int compareTo(Object other) {
		return this.numberOfTags - ((Video)other).numberOfTags;
	}

}