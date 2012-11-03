package nl.waisda.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import nl.waisda.domain.Game;
import nl.waisda.domain.Mission;
import nl.waisda.domain.Video;
import nl.waisda.services.MissionService;
import nl.waisda.services.ScoringService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MissionRepository extends AbstractRepository<Mission> {

	private Logger log = Logger.getLogger(MissionRepository.class);

	@Autowired
	private MissionService missionService;
	
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
		List<Mission> missions = query.getResultList();
		List<Mission> filteredMissions = new ArrayList<Mission>();
		for (Mission mission : missions) {
			boolean isComplete = true;
			List<Video> videos = missionService.getVideosForMission(mission.getId());
			for (Video video : videos) {
				if (!video.isTagComplete()) {
					isComplete = false;
				}
			}
			if (isComplete == false) {
				filteredMissions.add(mission);
			}
		}
		return filteredMissions;
	}
}