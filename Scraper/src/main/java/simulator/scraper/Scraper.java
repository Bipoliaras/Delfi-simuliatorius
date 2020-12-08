package simulator.scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import simulator.comments.CommentEndpoint;
import simulator.comments.CommentTypes;
import simulator.persistence.entities.headline.Headline;
import simulator.persistence.entities.image.Image;
import simulator.persistence.repositories.CommentRepository;
import simulator.persistence.repositories.HeadlineRepository;
import simulator.persistence.repositories.ImageRepository;


@Service
public class Scraper {

  private final HeadlineRepository headlineRepository;

  private final ImageRepository imageRepository;

  private final CommentRepository commentRepository;

  private final CommentEndpoint commentEndpoint;

  private static final Logger logger = LoggerFactory.getLogger(Scraper.class);

  public Scraper(HeadlineRepository headlineRepository,
      ImageRepository imageRepository,
      CommentRepository commentRepository, CommentEndpoint commentEndpoint) {
    this.headlineRepository = headlineRepository;
    this.imageRepository = imageRepository;
    this.commentRepository = commentRepository;
    this.commentEndpoint = commentEndpoint;
  }

  public void scrape() {
    Arrays.stream(WebsiteLinks.values()).forEach(link -> scrapeLink(link.getUrl()));
  }

  private void scrapeLink(String websiteLink) {
    try {

      Document doc = Jsoup.connect(websiteLink).get();

      List<String> links = new ArrayList<>();

      doc.select("a[href]")
          .stream()
          .map(element -> element.attr("href"))
          .filter(link -> link.contains("&com=1"))
          .map(link -> link.replace("&com=1", ""))
          .forEach(link ->
          {
            links.add(link);
            scrapeHeadlinesAndImages(link);
          });

      links.stream().map(
          link -> link.substring(link.indexOf('=') + 1)
      ).forEach(this::saveCommentsFromLink);

    } catch (Exception ex) {
      logger.error(ex.toString());
    }
  }

  private void saveCommentsFromLink(String link) {
    commentRepository
        .saveAll(commentEndpoint.getComments(Integer.parseInt(link), CommentTypes.ANONYMOUS_MAIN)
            .stream().filter(
                comment -> !comment.getText().equals("null") && !comment.getUsername()
                    .equals("null"))
            .collect(
                Collectors.toList()));
    commentRepository
        .saveAll(commentEndpoint.getComments(Integer.parseInt(link), CommentTypes.REGISTERED_MAIN)
            .stream().filter(
                comment -> !comment.getText().equals("null") && !comment.getUsername()
                    .equals("null")).collect(
                Collectors.toList()));
  }


  private void scrapeHeadlinesAndImages(String link) {

    try {

      Document doc = Jsoup.connect(link).get();

      headlineRepository.save(Headline.builder()
          .title(doc.select("h1").text())
          .date(doc.select("div.source-date").text())
          .build());

      doc.select("img[width=\"880\"],img[height=\"550\"]").stream()
          .map(element -> element.attr("src"))
          .forEach(imageLink -> imageRepository.save(Image.builder().imageLink(imageLink).build()));

    } catch (Exception ex) {
      logger.error(ex.toString());
    }

  }

}
