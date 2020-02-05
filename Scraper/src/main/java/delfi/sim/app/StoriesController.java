package delfi.sim.app;

import delfi.sim.entities.Story;
import delfi.sim.scraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stories/")
public class StoriesController {

  private static final int TOTAL_COMMENT_AMOUNT = 10;

  private Scraper scraper;

  @Autowired
  public void setScraper(Scraper scraper) {
    this.scraper = scraper;
  }

  @GetMapping
  public Story getRandomStory() {

//    Markov commentMarkov = new Markov();
//
//    commentRepository.findAll().forEach(comment -> commentMarkov.parseText(comment.getComment()));
//
//    Markov headlineMarkov = new Markov();
//
//    headlineRepository.findAll()
//        .forEach(headline -> headlineMarkov.parseText(headline.getHeadline()));
//
//    Story story = new Story();
//
//    story.setHeadline(headlineMarkov.generate());
//
//    story.setLink(linkRepository.findAll().get(0));
//
//    List<Comment> randomComments = new ArrayList<>();
//
//    for (int comment = 0; comment < TOTAL_COMMENT_AMOUNT; comment++) {
//      randomComments.add(new Comment(commentMarkov.generate()));
//    }
//
//    story.setComments(randomComments);
//
//    return story;

    return new Story();
  }

  @PostMapping
  public void addComments() {
    scraper.scrape();
  }


}
