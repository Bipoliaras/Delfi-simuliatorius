package delfi.sim.app;

import delfi.sim.entities.Comment;
import delfi.sim.entities.CommentRepository;
import delfi.sim.entities.Headline;
import delfi.sim.entities.HeadlineRepository;
import delfi.sim.entities.Story;
import delfi.sim.scraper.Scraper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stories/")
public class StoriesController {

  private static final int TOTAL_COMMENT_AMOUNT = 5;

  private Scraper scraper;

  private CommentRepository commentRepository;

  private HeadlineRepository headlineRepository;

  private Random random = new Random();

  @Autowired
  public void setCommentRepository(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Autowired
  public void setHeadlineRepository(HeadlineRepository headlineRepository) {
    this.headlineRepository = headlineRepository;
  }

  @Autowired
  public void setScraper(Scraper scraper) {
    this.scraper = scraper;
  }

  @GetMapping
  public Story getRandomStory() {

    List<Headline> headlines = headlineRepository.findAll();

    Headline randomHeadline = headlines.get(random.nextInt(headlines.size() - 1));

    List<Comment> comments = commentRepository.findAll();

    List<Comment> storyComments = new ArrayList<>();

    for(int i = 0 ; i < TOTAL_COMMENT_AMOUNT; i++) {
      storyComments.add(comments.get(random.nextInt(comments.size()-1)));
    }

    return Story.builder().comments(storyComments).headline(randomHeadline).build();
  }

  @PostMapping
  public void addComments() {
    scraper.scrape();
  }


}
