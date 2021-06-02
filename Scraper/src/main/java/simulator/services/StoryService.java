package simulator.services;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import simulator.persistence.entities.headline.Headline;
import simulator.persistence.entities.story.InterestType;
import simulator.persistence.entities.story.Story;
import simulator.persistence.repositories.CommentRepository;
import simulator.persistence.repositories.HeadlineRepository;
import simulator.persistence.repositories.ImageRepository;
import simulator.persistence.repositories.StoryRepository;
import simulator.scraper.Scraper;

@Service
public class StoryService {

  private final Scraper scraper;

  private final StoryRepository storyRepository;

  private final HeadlineRepository headlineRepository;

  private final CommentRepository commentRepository;

  private final ImageRepository imageRepository;

  private final Logger logger = LoggerFactory.getLogger(StoryService.class);

  @Autowired
  public StoryService(Scraper scraper, StoryRepository storyRepository,
      HeadlineRepository headlineRepository, CommentRepository commentRepository,
      ImageRepository imageRepository) {
    this.scraper = scraper;
    this.storyRepository = storyRepository;
    this.headlineRepository = headlineRepository;
    this.commentRepository = commentRepository;
    this.imageRepository = imageRepository;
  }

  public List<Story> getStories(Long limit) {
    return storyRepository.findRandomStories(limit);
  }

  public Story getStoryById(Long id) {
    return storyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Story not found", null));
  }

  @Scheduled(fixedDelay = 86400000, initialDelay = 10000)
  public void createStories() {
    //delete all entries from the database
    headlineRepository.deleteAll();
    storyRepository.deleteAll();
    commentRepository.deleteAll();
    imageRepository.deleteAll();

    //scrape new ones
    scraper.scrape();

    //create and save stories
    IntStream.range(0, 500).parallel()
        .mapToObj(number -> getRandomStory())
        .forEach(storyRepository::save);

  }

  private InterestType getInterestType() {
    return ThreadLocalRandom.current().nextInt(0, 2) == 1 ? InterestType.COLD : InterestType.HOT;
  }

  private Story getRandomStory() {
    Headline randomHeadline = headlineRepository.getRandomHeadline();

    return new Story(randomHeadline.getTitle(),
        randomHeadline.getDate(),
        imageRepository.findRandomImage().getImageLink(),
        getInterestType(),
        commentRepository.findRandomComments(7));
  }


}
