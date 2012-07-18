package nl.waisda.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.waisda.model.Util;

import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
public class Game {
	
	/** Time taken to give other players a chance to join the game. */
	public static final int QUEUE_TIME_MS = 20000;
	
	/** Allow users to submit entries for times at most this many ms in the past. */
	public static final int ACCEPTABLE_LAG_MS = 2 * 60 * 1000; // 2 minutes

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"EEEE d MMMM yyyy 'at' HH:mm", Util.ENGLISH_LOCALE);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(optional = false)
	private Video video;
	
	@ManyToOne(optional = true)
	private User initiator;
	
	@Basic(optional = true)
	private Date start;

	@Basic(optional = false)
	private int countExistingVideoTags;
	
	/*
	 * Business logic
	 */
	
	@JsonIgnore
	public int getElapsed() {
		return (int) (System.currentTimeMillis() - getStart().getTime());
	}
	
	public boolean acceptsNewTagEntries() {
		return getElapsed() < video.getDuration() + ACCEPTABLE_LAG_MS;
	}
	
	public GameState getGameState(int time) {
		if (hasEnded(time)) {
			// Check this first, in case this game has long since ended.
			return GameState.ENDED;
		} else if (time < 0) {
			return GameState.ABOUT_TO_START;
		} else {
			return GameState.ACTIVE;
		}
	}
	
	/**
	 * Returns whether the given time falls outside the video's duration and/or
	 * this game is very old.
	 */
	public boolean hasEnded(int time) {
		return time > video.getDuration() || !acceptsNewTagEntryAt(getElapsed());
	}
	
	public boolean hasEnded() {
		return getElapsed() > video.getDuration();
	}
	
	public boolean acceptsNewTagEntryAt(int time) {
		int elapsed = getElapsed();
		int lowerBound = Math.max(0, elapsed - ACCEPTABLE_LAG_MS);
		int upperBound = Math.min(elapsed, video.getDuration());
		return time >= lowerBound && time <= upperBound;
	}
	
	public String getFormattedDay() {
		DateFormat df = new SimpleDateFormat("EEEE dd-MM-yyyy", Util.ENGLISH_LOCALE);
		String s = df.format(start);
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public Date getEnd() {
		Calendar c = Calendar.getInstance();
		c.setTime(getStart());
		c.add(Calendar.MILLISECOND, getVideo().getDuration());
		return c.getTime();
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

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@JsonIgnore
	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public Date getStart() {
		return start;
	}

	public String getPrettyStart() {
		String s = DATE_FORMAT.format(start);
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public int getCountExistingVideoTags() {
		return countExistingVideoTags;
	}

	public void setCountExistingVideoTags(int countExistingVideoTags) {
		this.countExistingVideoTags = countExistingVideoTags;
	}

}