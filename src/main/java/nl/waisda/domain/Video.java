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
public class Video {

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

	@Enumerated(EnumType.STRING)
	private PlayerType playerType;

	@Basic
	private int fragmentID;

	/** Start time within episode, in ms. */
	@Basic
	private int startTime;

	/** Fragmentenrubriek zoals in MBH dump. */
	private int sectionNid;

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

	public int getFragmentID() {
		return fragmentID;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getSectionNid() {
		return sectionNid;
	}

	public String getPrettyDuration() {
		return TagEntry.getFriendlyTime(duration);
	}

}