package simulator;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simulator.Constants.InterestType;
import simulator.domain.entity.Comment;
import simulator.domain.entity.Story;
import simulator.repository.CommentRepository;
import simulator.repository.StoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoriesIT extends BaseIT {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private StoryRepository storyRepository;

  @BeforeEach
  public void setup() {

    List<Comment> comments = commentRepository.saveAll(
        List.of(
            new Comment("donger", "bonger"),
            new Comment("conger", "songer"),
            new Comment("fonger", "fonger"),
            new Comment("gonger", "gonger")
        )
    );

    storyRepository.saveAll(
        List.of(
            new Story("asd", "asd", "asd", InterestType.COLD, comments)
        )
    );

  }

  @Test
  void givenStories_whenGetStories_ok() {

    RestAssured
        .given()
        .accept(ContentType.JSON)
        .get("/stories")
        .then()
        .log()
        .all()
        .statusCode(200);

  }

}
