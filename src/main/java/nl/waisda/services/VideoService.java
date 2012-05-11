package nl.waisda.services;

import java.util.ArrayList;
import java.util.List;

import nl.waisda.domain.Video;
import nl.waisda.model.Cache;
import nl.waisda.model.Channel;
import nl.waisda.model.Value;
import nl.waisda.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

	public static final long MAX_CHANNEL_AGE = 60000;

	@Autowired
	private VideoRepository videoRepo;

	private Value<List<Channel>> channelContent;

	{
		channelContent = new Value<List<Channel>>() {

			@Override
			public List<Channel> get() {
				List<Video> videos = videoRepo.getFeaturedVideos();
				List<Channel> channels = new ArrayList<Channel>(videos.size());
				for (Video video : videos) {

					int highscore = videoRepo.getHighscore(video.getId());
					channels.add(new Channel(video, highscore));
				}
				return channels;
			}

		};
		channelContent = new Cache<List<Channel>>(channelContent,
				MAX_CHANNEL_AGE);
	}

	public Video getVideoById(int videoId) {
		return videoRepo.getById(videoId);
	}

	public List<Channel> getChannelContent() {
		return channelContent.get();
	}

}
