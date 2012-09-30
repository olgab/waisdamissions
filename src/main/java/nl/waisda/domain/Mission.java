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

import nl.waisda.model.Util;


@Entity
public class Mission {
	
	/** Mission Duration. */
	public static final int DURATION = 7 * 24 * 60 * 1000; // 7 days

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"EEEE d MMMM yyyy 'at' HH:mm", Util.ENGLISH_LOCALE);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Basic(optional = false)
	private String title;
	
	@Basic(optional = false)
	private String description;
	
	@Basic(optional = false)
	private Date start;

	@Basic(optional = false)
	private Date end;
	
	/*
	 * Business logic
	 */
	
	public MissionState getMissionState() {
		Date time = Calendar.getInstance().getTime();
		if (time.after(getEnd())) {
			return MissionState.ENDED;
		} else if (time.before(getStart())) {
			return MissionState.NOT_STARTED;
		} else {
			return MissionState.STARTED;
		}
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Date getEnd() {
		return end;
	}

	public String getPrettyEnd() {
		String s = DATE_FORMAT.format(end);
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}