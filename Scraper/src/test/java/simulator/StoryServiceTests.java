package simulator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import simulator.domain.entity.Comment;
import simulator.domain.entity.Headline;
import simulator.domain.entity.Image;
import simulator.repository.CommentRepository;
import simulator.repository.HeadlineRepository;
import simulator.repository.ImageRepository;
import simulator.repository.StoryRepository;
import simulator.scraper.Scraper;
import simulator.service.StoryService;

@SpringBootTest
class StoryServiceTests {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private StoryRepository storyRepository;

  @Autowired
  private HeadlineRepository headlineRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private StoryService storyService;

  @MockBean
  private Scraper scraper;

  private void saveEntries() {

    headlineRepository.saveAndFlush(new Headline("asd", "asd"));

    imageRepository.saveAndFlush(new Image("http"));

    List<Comment> comments = commentRepository.saveAll(
        List.of(
            new Comment("donger", "bonger"),
            new Comment("conger", "songer"),
            new Comment("fonger", "fonger"),
            new Comment("gonger", "gonger")
        )
    );

  }

  @Test
  void generatesStoriesCorrectly() {

    Mockito.doAnswer(id -> { saveEntries(); return null; }).when(scraper).scrape();

    storyService.createStories();

    assertThat(storyRepository.findAll())
        .isNotEmpty()
        .isNotNull()
        .hasSize(500);
  }


}
