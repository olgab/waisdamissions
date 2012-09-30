package nl.waisda.repositories;

import nl.waisda.domain.MissionGame;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MissionGameRepository extends AbstractRepository<MissionGame> {

    private final Logger log = Logger.getLogger(MissionGameRepository.class);

    public MissionGameRepository() {
        super(MissionGame.class);
    }

    @Transactional
    @Override
    public void store(final MissionGame missionGame) {
        super.store(missionGame);
    }
}