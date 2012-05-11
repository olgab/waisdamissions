package nl.waisda.repositories;

import java.util.List;

import javax.persistence.TypedQuery;

import nl.waisda.domain.Video;

import org.springframework.stereotype.Repository;

@Repository
public class VideoRepository extends AbstractRepository<Video> {

	public static final int NCHANNELS = 6;

	public VideoRepository() {
		super(Video.class);
	}

	/**
	 * Currently this method makes a random, unbiased selection. If you would
	 * like to filter certain video's, or use statistics to influence the video
	 * selection, you can do that here.
	 */
	public List<Video> getFeaturedVideos() {
		String q = "SELECT v FROM Video v WHERE v.enabled = true ORDER BY RAND()";
		// Hibernate requires the "= true"

		TypedQuery<Video> query = getEntityManager()
				.createQuery(q, Video.class);
		query.setMaxResults(NCHANNELS);
		return query.getResultList();
	}

	public int getHighscore(int videoId) {
		String q = "SELECT MAX(score) FROM (SELECT SUM(t.score) AS score "
				+ "FROM TagEntry t INNER JOIN Game g on t.game_id = g.id "
				+ "WHERE g.video_id = :videoId GROUP BY g.id) games";
		Object result = getEntityManager().createNativeQuery(q)
				.setParameter("videoId", videoId).getSingleResult();
		if (result == null) {
			return 0;
		} else {
			return ((Number) result).intValue();
		}
	}

	public List<Video> getTopVideosForTag(String normalizedTag, int maxResults) {
		String q = "SELECT t.game.video FROM TagEntry t "
				+ "WHERE t.normalizedTag = :normalizedTag "
				+ "GROUP BY t.game.video.id ORDER BY COUNT(t.game.video.id) DESC";

		TypedQuery<Video> query = getEntityManager()
				.createQuery(q, Video.class);
		query.setParameter("normalizedTag", normalizedTag);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}

}
