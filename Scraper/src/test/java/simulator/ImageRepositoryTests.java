package simulator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simulator.domain.entity.Headline;
import simulator.domain.entity.Image;
import simulator.repository.HeadlineRepository;
import simulator.repository.ImageRepository;

@SpringBootTest
class ImageRepositoryTests {

  @Autowired
  private ImageRepository imageRepository;

  @Test
  void saveOk() {
    Image expected = new Image("http://imgur.com/1515");

    Image actual = imageRepository.saveAndFlush(expected);

    assertThat(actual.getImageLink()).isEqualTo(expected.getImageLink());
    assertThat(actual.getId()).isNotNull();

  }


}
