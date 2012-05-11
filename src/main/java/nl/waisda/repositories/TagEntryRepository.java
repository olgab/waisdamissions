package nl.waisda.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import nl.waisda.domain.TagEntry;
import nl.waisda.domain.User;
import nl.waisda.domain.UserScore;
import nl.waisda.model.TagCloudItem;
import nl.waisda.services.ScoringService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class TagEntryRepository extends AbstractRepository<TagEntry> {

	private Logger log = Logger.getLogger(TagEntryRepository.class);
	
	@Autowired
	private ScoringService scoringService;

	public TagEntryRepository() {
		super(TagEntry.class);
	}
	
	@Override
	public void store(TagEntry entity) {
		super.store(entity);
		getEntityManager().flush();
		getEntityManager().refresh(entity);
	}

	public List<TagEntry> getRecentEntries(int gameId, int aroundTime) {
		log.info(String.format("getRecentEntries(%d, %d)", gameId, aroundTime));
		return getEntityManager()
			.createQuery("SELECT t FROM TagEntry t WHERE t.game.id = :gameId AND t.gameTime BETWEEN :t1 AND :t2", TagEntry.class)
			.setParameter("gameId", gameId)
			.setParameter("t1", aroundTime - ScoringService.MAX_LOOKBACK_TIME)
			.setParameter("t2", aroundTime + ScoringService.MAX_LOOKBACK_TIME)
		.getResultList();
	}
	
	public boolean alreadyEntered(int gameId, String normalizedTag,
			int aroundTime, int ownerId) {
		return !getEntityManager()
				.createQuery(
						"SELECT t FROM TagEntry t " +
						"WHERE t.game.id = :gameId " +
						"AND t.normalizedTag = :normalizedTag " +
						"AND t.gameTime BETWEEN :t1 AND :t2 " +
						"AND t.owner.id = :ownerId",
						TagEntry.class)
				.setParameter("gameId", gameId)
				.setParameter("normalizedTag", normalizedTag)
				.setParameter("t1",
						aroundTime - ScoringService.MAX_LOOKBACK_TIME)
				.setParameter("t2",
						aroundTime + ScoringService.MAX_LOOKBACK_TIME)
				.setParameter("ownerId", ownerId).getResultList().isEmpty();
	}

	@SuppressWarnings("unchecked")
	public List<TagEntry> getMatches(int videoId, String normalizedTag,
			int aroundTime) {
		String q = "SELECT t.* FROM TagEntry t "
				+ "INNER JOIN Game g ON t.game_id = g.id "
				+ "WHERE g.video_id = :videoId "
				+ "  AND t.normalizedTag = :normalizedTag "
				+ "  AND t.gameTime between :t1 AND :t2 "
				+ "UNION "
				+ "SELECT t.* FROM TagEntry t "
				+ "INNER JOIN MatchingTag s on s.lo = t.normalizedTag "
				+ "INNER JOIN Game g on g.id = t.game_id "
				+ "WHERE s.hi = :normalizedTag "
				+ "  AND g.video_id = :videoId "
				+ "  AND t.gametime between :t1 AND :t2 "
				+ "UNION "
				+ "SELECT t.* FROM TagEntry t "
				+ "INNER JOIN MatchingTag s on s.hi = t.normalizedTag "
				+ "INNER JOIN Game g on g.id = t.game_id "
				+ "WHERE s.lo = :normalizedTag "
				+ "  AND g.video_id = :videoId "
				+ "  AND t.gametime between :t1 AND :t2";
		return getEntityManager()
				.createNativeQuery(q, TagEntry.class)
				.setParameter("videoId", videoId)
				.setParameter("normalizedTag", normalizedTag)
				.setParameter("t1",
						aroundTime - ScoringService.MAX_LOOKBACK_TIME)
				.setParameter("t2",
						aroundTime + ScoringService.MAX_LOOKBACK_TIME)
				.getResultList();
	}

	public List<TagEntry> getEntries(int gameId, int ownerId) {
		return getEntityManager()
			.createQuery("SELECT t FROM TagEntry t WHERE t.game.id = :gameId AND t.owner.id = :ownerId", TagEntry.class)
			.setParameter("gameId", gameId)
			.setParameter("ownerId", ownerId)
		.getResultList();
	}
	
	public List<TagEntry> getEntries(int gameId) {
		return getEntityManager()
				.createQuery(
						"SELECT t FROM TagEntry t WHERE t.game.id = :gameId",
						TagEntry.class).setParameter("gameId", gameId)
				.getResultList();
	}

	public List<TagEntry> getUnfairTagEntries(int ownerId) {
		return getEntityManager()
				.createQuery(
						"SELECT t FROM TagEntry t WHERE t.owner.id = :ownerId "
								+ "AND t.matchingTagEntry.owner.id = :ownerId",
						TagEntry.class).setParameter("ownerId", ownerId)
				.getResultList();
	}

	public List<UserScore> getParticipants(int gameId) {
		String q = "select p.user_id, u.name, ifnull(sum(t.score), 0) as score " +
				"from Participant p " +
				"inner join User u on p.user_id = u.id " +
				"left join TagEntry t on p.user_id = t.owner_id and p.game_id = t.game_id " +
				"where p.game_id = :gameId " +
				"group by p.user_id, p.game_id " +
				"order by score desc";
		List<?> arrays = getEntityManager().createNativeQuery(q).setParameter("gameId", gameId).getResultList();
		
		List<UserScore> res = new ArrayList<UserScore>(arrays.size());
		
		for (Object row : arrays) {
			Object[] values = (Object[]) row;
			User u = new User();
			u.setId((int) (Integer) values[0]);
			u.setName((String) values[1]);
			res.add(new UserScore(u, ((Number) values[2]).intValue()));
		}

		return res;
	}

	@Transactional
	public void moveTagEntries(User source, User target) {
		Query q = getEntityManager().createQuery(
				"UPDATE TagEntry SET owner = :target WHERE owner = :source");
		q.setParameter("source", source);
		q.setParameter("target", target);
		int n = q.executeUpdate();
		log.info(String.format("Moved %d tag entries from user %d to user %d",
				n, source.getId(), target.getId()));

		for (TagEntry t : getUnfairTagEntries(target.getId())) {
			log.info("Reowning tag " + t.getId());
			scoringService.updateMatchAndStore(t, false);
		}
	}
	
	public int countTags() {
		Query q = getEntityManager().createQuery("SELECT COUNT(*) FROM TagEntry");
		return (int) (long) (Long) q.getSingleResult();
	}

	public int countTags(int videoId) {
		Query q = getEntityManager().createQuery(
				"SELECT COUNT(*) FROM TagEntry t WHERE t.game.video.id = :videoId");
		q.setParameter("videoId", videoId);
		return ((Number)q.getSingleResult()).intValue();
	}

	public int countMatches() {
		Query q = getEntityManager().createQuery("SELECT COUNT(*) FROM TagEntry WHERE matchingTagEntry_id IS NOT NULL");
		return (int) (long) (Long) q.getSingleResult();
	}
	
	public List<TagCloudItem> getTagCloud() {
		// We order by decreasing count so that we may easily assign
		// relativeSizes in the for loop below.
		String q = "select t.normalizedTag from TagEntry t "
				+ "where creationDate >= now() - interval 7 day "
				+ "group by t.normalizedTag "
				+ "order by count(*) desc limit 20";
		@SuppressWarnings("unchecked")
		List<String> tags = getEntityManager().createNativeQuery(q)
				.getResultList();
		List<TagCloudItem> cloud = new ArrayList<TagCloudItem>(tags.size());
		int i = 0;
		for (String tag : tags) {
			int size = 5 - i * 5 / tags.size();
			TagCloudItem item = new TagCloudItem(tag, size);
			cloud.add(item);
			i++;
		}
		Collections.shuffle(cloud);
		return cloud;
	}
	
	public int countPioneerMatchesSince(int ownerId, Date since) {
		String q = "select count(t) from TagEntry t inner join t.matchingTagEntry t2 "
				+ "where t.owner.id = :ownerId and t.pioneer = true "
				+ "and t.game.id < t2.game.id and t2.creationDate >= :since ";
		return (int) (long) getEntityManager().createQuery(q, Long.class)
				.setParameter("ownerId", ownerId).setParameter("since", since)
				.getSingleResult();
	}

	public List<TagEntry> getLastPioneerMatches(int ownerId, int count) {
		String q = "select t from TagEntry t inner join t.matchingTagEntry t2 "
				+ "where t.owner.id = :ownerId and t.pioneer = true "
				+ "order by t.creationDate desc";
		return getEntityManager().createQuery(q, TagEntry.class)
				.setParameter("ownerId", ownerId)
				.setMaxResults(count).getResultList();
	}

	public TagEntry getFirstEntry(String normalizedTag) {
		String q = "SELECT t FROM TagEntry t "
				+ "WHERE t.normalizedTag = :normalizedTag "
				+ "ORDER BY t.creationDate ASC";
		List<TagEntry> tags = getEntityManager().createQuery(q, TagEntry.class)
				.setParameter("normalizedTag", normalizedTag).setMaxResults(1)
				.getResultList();
		return tags.isEmpty() ? null : tags.get(0);
	}

	public List<String> getTopTags(int videoId, int maxResults) {
		String q = "SELECT t.normalizedTag FROM TagEntry t "
				+ "WHERE t.game.video.id = :videoId "
				+ "GROUP BY t.normalizedTag ORDER BY COUNT(*) DESC";
		return getEntityManager().createQuery(q, String.class)
				.setParameter("videoId", videoId).setMaxResults(maxResults)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> getDictionariesContaining(String tag) {
		String normalizedTag = TagEntry.normalize(tag);

		Query query = getEntityManager().createNativeQuery(
				"SELECT e.dictionary FROM DictionaryEntry e "
						+ "WHERE e.normalizedTag = :normalizedTag");
		query.setParameter("normalizedTag", normalizedTag);

		return query.getResultList();
	}

}
