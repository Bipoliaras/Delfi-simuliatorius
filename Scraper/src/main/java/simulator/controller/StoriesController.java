package simulator.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import simulator.domain.entity.Story;
import simulator.service.StoryService;


@RestController
@RequestMapping("/stories")
public class StoriesController {

  private final StoryService storyService;

  public StoriesController(StoryService storyService) {
    this.storyService = storyService;
  }

  @GetMapping
  public List<Story> getStories(
      @RequestParam(name = "limit", required = false, defaultValue = "20") int limit) {
    return storyService.getStories(limit);
  }

  @GetMapping("/{id}")
  public Story getStoryById(@PathVariable("id") Long id) {
    return storyService.getStoryById(id);
  }

}
