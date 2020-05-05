package simulator.services;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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

  private Scraper scraper;

  private StoryRepository storyRepository;

  private HeadlineRepository headlineRepository;

  private CommentRepository commentRepository;

  private ImageRepository imageRepository;

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

  public List<Story> getStories() {
    return storyRepository.findRandomStories(10);
  }

  public Story getStoryById(Long id) {
    return storyRepository.findById(id).get();
  }

  @Scheduled(fixedDelay = 86400000, initialDelay = 1000)
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

    return Story.builder()
        .comments(commentRepository.findRandomComments(7))
        .createdOn(randomHeadline.getDate())
        .imageSrc(imageRepository.findRandomImage().getImageLink())
        .interest(getInterestType())
        .title(randomHeadline.getTitle())
        .build();
  }


}
