package simulator;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.domain.entity.Comment;
import simulator.markov.Markov;

class MarkovTests {

  private final Markov markov = new Markov();

  private final Logger logger = LoggerFactory.getLogger(MarkovTests.class);

  private final TestUtils testUtils = new TestUtils();

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void givenCommentText_whenGenerateMarkovText_thenGeneratesText() throws IOException {
    String commentsJson = testUtils.getFileAsString("json/comments.json");

    JSONObject jsonObject = new JSONObject(commentsJson);

    Comment[] comments = objectMapper.readValue(jsonObject.get("comments").toString(), Comment[].class);

    assertThat(comments)
        .isNotNull()
        .isNotEmpty()
        .hasSize(200);

    Arrays.stream(comments)
        .forEach(comment -> markov.parseText(comment.getText()));

    String randomText = markov.generate();

    logger.info("Generated text - {}", randomText);

    assertThat(randomText)
        .isNotNull()
        .isNotBlank();

  }

}
