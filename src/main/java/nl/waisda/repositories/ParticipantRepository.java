package nl.waisda.repositories;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nl.waisda.domain.Participant;
import nl.waisda.domain.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ParticipantRepository extends AbstractRepository<Participant> {

	private Logger log = Logger.getLogger(ParticipantRepository.class);

	public ParticipantRepository() {
		super(Participant.class);
	}

	@Transactional
	@Override
	public void store(Participant entity) {
		super.store(entity);
	}

	public Participant get(int userId, int gameId) {
		TypedQuery<Participant> query = getEntityManager().createQuery(
				"SELECT p FROM Participant p "
						+ "WHERE p.user.id = :userId AND p.game.id = :gameId",
				Participant.class);
		query.setParameter("userId", userId);
		query.setParameter("gameId", gameId);
		List<Participant> results = query.getResultList();
		log.info(String.format("Found %d matching participants", results.size()));
		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

	@Transactional
	public void moveParticipants(User source, User target) {
		Query q = getEntityManager().createQuery(
				"UPDATE Participant SET user = :target WHERE user = :source");
		q.setParameter("source", source);
		q.setParameter("target", target);
		int n = q.executeUpdate();
		log.info(String.format("Moved %d participants from user %d to user %d",
				n, source.getId(), target.getId()));
	}

	public int countCurrentlyPlaying() {
		Query query = getEntityManager()
				.createNativeQuery(
						"select count(distinct p.user_id) from Participant p "
								+ "inner join Game g on p.game_id = g.id "
								+ "inner join Video v on g.video_id = v.id "
								+ "where g.start - interval 60 second <= now() "
								+ "and now() <= g.start + interval (v.duration / 1000) second");
		return ((Number) query.getSingleResult()).intValue();
	}

}