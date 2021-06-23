package simulator;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simulator.domain.entity.Comment;
import simulator.domain.model.CommentResponse;
import simulator.repository.CommentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentsIT extends BaseIT {

  @Autowired
  private CommentRepository commentRepository;

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

  }

  @Test
  void getCommentsOk() {
    CommentResponse commentResponse = RestAssured.get("/comments")
        .then()
        .log()
        .all()
        .statusCode(200)
        .extract()
        .as(CommentResponse.class);

    assertThat(commentResponse.getComments())
        .hasSize(1)
        .isNotNull()
        .isNotEmpty();
  }


}
