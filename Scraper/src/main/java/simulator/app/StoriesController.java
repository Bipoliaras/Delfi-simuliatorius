package simulator.app;

import org.springframework.web.bind.annotation.PathVariable;
import simulator.persistence.repositories.CommentRepository;
import simulator.persistence.repositories.HeadlineRepository;
import simulator.persistence.repositories.ImageRepository;
import simulator.persistence.entities.story.Story;
import simulator.scraper.Scraper;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.services.StoryService;


@RestController
@RequestMapping("/stories/")
public class StoriesController {

  private StoryService storyService;

  public StoriesController(StoryService storyService) {
    this.storyService = storyService;
  }

  @GetMapping
  public List<Story> getStories() {
    return storyService.getStories();
  }

  @GetMapping("{id}")
  public Story getStoryById(@PathVariable ("id") Long id) {
    return storyService.getStoryById(id);
  }

}
