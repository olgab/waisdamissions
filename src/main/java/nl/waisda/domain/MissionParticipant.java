package nl.waisda.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class MissionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional = false)
    private Mission mission;

    @Cascade({CascadeType.ALL})
    @ManyToOne(optional = false)
    private Participant participant;

    /*
     * Getters and setters
     */

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(final Mission mission) {
        this.mission = mission;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(final Participant participant) {
        this.participant = participant;
    }
}