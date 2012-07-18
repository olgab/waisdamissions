package nl.waisda.domain;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import nl.waisda.model.Util;

import org.hibernate.annotations.Formula;
import org.jasypt.util.password.StrongPasswordEncryptor;

@Entity
public class User implements Comparable<User> {

	public static final int NAVATARS = 8;

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd-MM-yyyy");

	private static final DateFormat CREATIONDATE_FORMAT = 
			new SimpleDateFormat("EEEE d MMMM yyyy", Util.DUTCH_LOCALE);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * No email address means a user is anonymous. Anonymous users cannot login
	 * explicitly, but they can participate in games. When they later
	 * authenticate themselves as an existing user, the anonymous tag entries
	 * are merged into that user's profile.
	 */
	@Basic
	@Column(unique = true)
	private String email;

	@Basic
	@Column(name = "password")
	private String saltedPassword;

	@Basic
	private String name;

	@Basic(optional = false)
	private Date creationDate;

	// Decoratieve profielvelden

	@Basic(optional = true)
	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Basic(optional = true)
	private String usernameTwitter;

	@Basic(optional = true)
	private String usernameHyves;

	@Basic(optional = true)
	private String usernameFacebook;

	// Afgeleide data

	@Formula("IFNULL((SELECT SUM(te.score) FROM TagEntry te WHERE te.owner_id = id), 0)")
	private int totalScore;

	@Formula("(SELECT COUNT(*) FROM TagEntry te WHERE te.owner_id = id)")
	private int totalTags;

	@Formula("(SELECT COUNT(*) FROM TagEntry te WHERE te.owner_id = id AND te.matchingTagEntry_id IS NOT NULL)")
	private int totalMatches;

	@Transient
	private int countNewPioneerMatches;

	/* Logic */

	public boolean isAnonymous() {
		return email == null;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		setSaltedPassword(passwordEncryptor.encryptPassword(plainTextPassword));
	}

	public boolean isPlayerBarVisible() {
		return !isAnonymous() || getTotalScore() > 0;
	}
	
	public String getPrettyCreationDate() {
		return CREATIONDATE_FORMAT.format(creationDate);
	}

	/* Getters and setters */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name != null ? name : "Guest";
	}

	public void setName(String name) {
		this.name = name != null ? name.trim() : null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getSaltedPassword() {
		return saltedPassword;
	}

	public void setSaltedPassword(String saltedPassword) {
		this.saltedPassword = saltedPassword;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCreationDate() {
		setCreationDate(new Date());
	}

	public String getAvatarUrl() {
		return getGravatarUrl(email, 150);
	}

	public String getSmallAvatarUrl() {
		return getGravatarUrl(email, 30);
	}

	public String getGravatarUrl(String email, int size) {
		return String.format(
				"http://www.gravatar.com/avatar/%s?s=%d&d=identicon",
				getGravatarHash(), size);
	}

	private String getGravatarHash() {
		String input = email;
		if (input != null) {
			input = input.trim().toLowerCase();
		}
		if (input == null || input.isEmpty()) {
			input = String.valueOf(id);
		}
		try {
			final String charset = "UTF-8";
			byte[] emailBytes = input.getBytes(charset);
			byte[] digest = MessageDigest.getInstance("MD5").digest(emailBytes);
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		if (dateOfBirth == null) {
			return 0;
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());

			Calendar dob = Calendar.getInstance();
			dob.setTime(dateOfBirth);

			int dy = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
			dob.add(Calendar.YEAR, dy);
			if (dob.compareTo(now) > 0) {
				dy--;
			}
			return dy;
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getUsernameTwitter() {
		return usernameTwitter;
	}

	public void setUsernameTwitter(String usernameTwitter) {
		this.usernameTwitter = usernameTwitter;
	}

	public String getUsernameHyves() {
		return usernameHyves;
	}

	public void setUsernameHyves(String usernameHyves) {
		this.usernameHyves = usernameHyves;
	}

	public String getUsernameFacebook() {
		return usernameFacebook;
	}

	public void setUsernameFacebook(String usernameFacebook) {
		this.usernameFacebook = usernameFacebook;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getTotalTags() {
		return totalTags;
	}

	public int getTotalMatches() {
		return totalMatches;
	}

	public int getCountNewPioneerMatches() {
		return countNewPioneerMatches;
	}

	public void setCountNewPioneerMatches(int countNewPioneerMatches) {
		this.countNewPioneerMatches = countNewPioneerMatches;
	}

	public String getShortDescription() {
		if (isAnonymous()) {
			return String.format("Anonymous user %d", getId());
		} else {
			return String.format("User %d (%s)", getId(), getName());
		}
	}

	@Override
	public int compareTo(User other) {
		return this.id - other.id;
	}

}
