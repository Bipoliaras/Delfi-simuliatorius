package simulator.scraper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import simulator.Constants.CommentTypes;
import simulator.Constants.WebsiteLinks;
import simulator.comments.CommentEndpoint;
import simulator.domain.entity.Headline;
import simulator.domain.entity.Image;
import simulator.repository.CommentRepository;
import simulator.repository.HeadlineRepository;
import simulator.repository.ImageRepository;


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
    Arrays.stream(WebsiteLinks.values())
        .map(WebsiteLinks::getUrl)
        .forEach(this::scrapeLink);
  }

  private void scrapeLink(String websiteLink) {
    try {

      Document doc = Jsoup.connect(websiteLink).get();

      List<String> links = doc.select("a[href]")
          .stream()
          .map(element -> element.attr("href"))
          .filter(link -> link.contains("&com=1"))
          .map(link -> link.replace("&com=1", ""))
          .peek(this::scrapeHeadlinesAndImages)
          .collect(Collectors.toList());

      links.stream()
          .map(link -> UriComponentsBuilder.fromUriString(link).build().getQueryParams())
          .map(parameterMap -> parameterMap.get("id"))
          .flatMap(Collection::stream)
          .map(Long::valueOf)
          .forEach(this::saveCommentsFromLink);

    } catch (Exception ex) {
      logger.error(ex.toString());
    }
  }

  private void saveCommentsFromLink(Long articleId) {
    commentRepository
        .saveAll(commentEndpoint.getComments(articleId, CommentTypes.ANONYMOUS_MAIN));
    commentRepository
        .saveAll(commentEndpoint.getComments(articleId, CommentTypes.REGISTERED_MAIN));
  }


  private void scrapeHeadlinesAndImages(String link) {

    try {

      Document doc = Jsoup.connect(link).get();

      headlineRepository.save(new Headline(doc.select("h1").text(),
          doc.select("div.source-date").text()));

      doc.select("img[width=\"880\"],img[height=\"550\"]").stream()
          .map(element -> element.attr("src"))
          .forEach(imageLink -> imageRepository.save(new Image(imageLink)));

    } catch (Exception ex) {
      logger.error(ex.toString());
    }

  }

}
