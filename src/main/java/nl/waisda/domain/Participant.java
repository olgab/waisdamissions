package nl.waisda.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(optional = false)
	private User user;

	@ManyToOne(optional = false)
	private Game game;

	@Basic(optional = false)
	private Date joinedOn;

	public Participant() {

	}

	public Participant(User user, Game game) {
		this.user = user;
		this.game = game;
		this.joinedOn = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Date getJoinedOn() {
		return joinedOn;
	}

	public void setJoinedOn(Date joinedOn) {
		this.joinedOn = joinedOn;
	}

}
