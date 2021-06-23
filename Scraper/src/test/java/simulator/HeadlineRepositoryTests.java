package simulator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simulator.domain.entity.Headline;
import simulator.repository.HeadlineRepository;

@SpringBootTest
class HeadlineRepositoryTests {

  @Autowired
  private HeadlineRepository headlineRepository;

  @Test
  void saveOk() {

    String name = "Massive headline";

    String date = ZonedDateTime.now().toString();

    Headline expected = new Headline(name, date);

    Headline actual = headlineRepository.saveAndFlush(expected);

    assertThat(actual.getDate()).isEqualTo(expected.getDate());
    assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
    assertThat(actual.getId()).isNotNull();

  }


}
