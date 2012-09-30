package nl.waisda.repositories;

import nl.waisda.domain.MissionParticipant;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MissionParticipantRepository extends AbstractRepository<MissionParticipant> {

    private final Logger log = Logger.getLogger(MissionParticipantRepository.class);

    public MissionParticipantRepository() {
        super(MissionParticipant.class);
    }

    @Transactional
    @Override
    public void store(final MissionParticipant missionParticipant) {
        super.store(missionParticipant);
    }
}