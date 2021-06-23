package simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils {

  private final Path resourcesDirectory = Path.of("", "src/test/resources");

  private final Logger logger = LoggerFactory.getLogger(TestUtils.class);

  public String getFileAsString(String path) throws IOException {
      Path file = this.resourcesDirectory.resolve(path);
      return Files.readString(file);
  }

}
