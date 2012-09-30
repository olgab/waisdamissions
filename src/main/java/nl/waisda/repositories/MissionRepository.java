package nl.waisda.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import nl.waisda.domain.Game;
import nl.waisda.domain.Mission;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


@Repository
public class MissionRepository extends AbstractRepository<Mission> {

	private Logger log = Logger.getLogger(MissionRepository.class);

	public MissionRepository() {
		super(Mission.class);
	}
	
	public List<Mission> getMissions() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		
		TypedQuery<Mission> query = getEntityManager().createQuery(
				"SELECT m from Mission m WHERE :d BETWEEN m.start and m.end",
				Mission.class);
		query.setParameter("d", date);
		return query.getResultList();
	}
}