package nl.waisda.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import nl.waisda.domain.TagEntry;
import nl.waisda.domain.Video;
import nl.waisda.exceptions.NotFoundException;
import nl.waisda.model.TagEntryStats;
import nl.waisda.model.VideoStats;
import nl.waisda.repositories.TagEntryRepository;
import nl.waisda.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TagController {

	@Autowired
	private TagEntryRepository tagEntryRepo;

	@Autowired
	private VideoRepository videoRepo;

	@RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
	public String profile(@PathVariable String tag, ModelMap model,
			HttpSession session) throws NotFoundException {
		String normalizedTag = TagEntry.normalize(tag);
		List<Video> videos = videoRepo.getTopVideosForTag(normalizedTag, 6);

		TagEntryStats stats = new TagEntryStats();
		stats.setNormalizedTag(normalizedTag);
		stats.setFirstEntry(tagEntryRepo.getFirstEntry(normalizedTag));

		for (Video v : videos) {
			VideoStats vs = new VideoStats(v,
					tagEntryRepo.getTopTags(v.getId(), 5));
			stats.getVideoStats().add(vs);
		}

		model.put("tagEntryStats", stats);
		return "tag";
	}
}
