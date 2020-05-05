package simulator.app;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.persistence.entities.story.Story;
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
  public Story getStoryById(@PathVariable("id") Long id) {
    return storyService.getStoryById(id);
  }

}
