package nl.waisda.services;

import java.util.List;

import nl.waisda.domain.TagEntry;
import nl.waisda.model.Cache;
import nl.waisda.model.GlobalStats;
import nl.waisda.model.TagCloudItem;
import nl.waisda.model.TopScores;
import nl.waisda.model.Value;
import nl.waisda.repositories.ParticipantRepository;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.repositories.UserRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoringService {

	public static int MAX_LOOKBACK_TIME = 10000;

	private static final Logger log = Logger.getLogger(ScoringService.class);

	@Autowired
	private TagEntryRepository tagEntryRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ParticipantRepository participantRepo;

	private Value<GlobalStats> globalStatsCache;

	{
		Value<GlobalStats> fetchGlobalStats = new Value<GlobalStats>() {

			@Override
			public GlobalStats get() {
				int countTags = tagEntryRepo.countTags();
				int countMatches = tagEntryRepo.countMatches();
				List<TagCloudItem> tagCloud = tagEntryRepo.getTagCloud();
				TopScores topScores = userRepo.getTopScores();
				int currentlyPlaying = participantRepo.countCurrentlyPlaying();
				return new GlobalStats(countTags, countMatches, tagCloud,
						topScores, currentlyPlaying);
			}
		};
		globalStatsCache = new Cache<GlobalStats>(fetchGlobalStats, 10000);
	}

	public void updateDictionary(TagEntry tagEntry) {
		List<String> dictionaryEntries = tagEntryRepo.getDictionariesContaining(tagEntry.getTag());
		if (dictionaryEntries.size() > 0) {
			// If multiple entries are found, use only the first one.
			tagEntry.setDictionary(dictionaryEntries.get(0));
		}
	}

	@Transactional
	public void updateMatchAndStore(TagEntry tagEntry,
			boolean updateReverseMatches) {

		// This method might be called as a result of a user merge. Check if we
		// already have a good match.
		TagEntry currentMatch = tagEntry.getMatchingTagEntry();
		if (currentMatch != null) {
			if (currentMatch.getOwner().getId() != tagEntry.getOwner().getId()) {
				// The old match is still okay.
				return;
			} else {
				// Old match is now a match with same owner. Try and find a new
				// one.
				tagEntry.setMatchingTagEntry(null);
			}
		}

		// Fetch all possible matches.
		List<TagEntry> matches = tagEntryRepo.getMatches(tagEntry.getGame()
				.getVideo().getId(), tagEntry.getNormalizedTag(),
				tagEntry.getGameTime());

		// First try and find a literal match from the user's history. If found,
		// awards no points.
		for (TagEntry match : matches) {
			if (match.getId() != tagEntry.getId()
					&& match.getOwner().getId() == tagEntry.getOwner().getId()
					&& match.getNormalizedTag().equals(
							tagEntry.getNormalizedTag())) {
				// Literal match with a previous entry by the same owner. Set
				// forward matching tag entry, awarding no points. Don't set
				// reverse match to allow for real matches later on.
				tagEntry.setMatchingTagEntry(match);
				break;
			}
		}

		// If no match is found yet, try and find one of another user.
		if (tagEntry.getMatchingTagEntry() == null) {
			for (TagEntry match : matches) {
				if (match.getOwner().getId() != tagEntry.getOwner().getId()) {
					tagEntry.setMatchingTagEntry(match);
					break;
				}
			}
		}

		// If still no match is found, tag entry is a pioneer and will earn more
		// points once a reverse match is found.
		if (tagEntry.getMatchingTagEntry() == null) {
			tagEntry.setPioneer(true);
		}

		tagEntry.updateScore();
		tagEntryRepo.store(tagEntry);

		if (updateReverseMatches && tagEntry.isOriginal()) {
			for (TagEntry match : matches) {
				if (match.getMatchingTagEntry() == null) {
					log.info(String.format("Awarding pioneer points to tag %d",
							match.getId()));
					match.setMatchingTagEntry(tagEntry);
					match.updateScore();
					tagEntryRepo.store(match);
				}
			}
		}
	}

	public GlobalStats getGlobalStats() {
		return globalStatsCache.get();
	}

}
