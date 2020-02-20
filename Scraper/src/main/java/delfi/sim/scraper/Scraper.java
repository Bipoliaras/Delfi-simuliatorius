package delfi.sim.scraper;

import delfi.sim.comments.CommentEndpoint;
import delfi.sim.comments.CommentTypes;
import delfi.sim.entities.CommentRepository;
import delfi.sim.entities.Headline;
import delfi.sim.entities.HeadlineRepository;
import delfi.sim.entities.Image;
import delfi.sim.entities.ImageRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Scraper {

  private static final String WEBSITE = "https://www.delfi.lt/";

  private CommentRepository commentRepository;

  private HeadlineRepository headlineRepository;

  private ImageRepository imageRepository;

  private CommentEndpoint commentEndpoint;

  private Logger logger = LoggerFactory.getLogger(Scraper.class);

  @Autowired
  public void setCommentRepository(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Autowired
  public void setHeadlineRepository(HeadlineRepository headlineRepository) {
    this.headlineRepository = headlineRepository;
  }

  @Autowired
  public void setImageRepository(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Autowired
  public void setCommentEndpoint(CommentEndpoint commentEndpoint) {
    this.commentEndpoint = commentEndpoint;
  }

  public void scrape() {

    try {

      Document doc = Jsoup.connect(WEBSITE).get();

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
      ).forEach(
          link ->
          {
            commentEndpoint.scrapeComments(Integer.parseInt(link), CommentTypes.ANONYMOUS_MAIN);
            commentEndpoint.scrapeComments(Integer.parseInt(link), CommentTypes.REGISTERED_MAIN);
          }
      );

    } catch (Exception ex) {
      logger.error(ex.toString());
    }

  }

  private void scrapeHeadlinesAndImages(String link) {

    try {

      Document doc = Jsoup.connect(link).get();

      String articleText = doc.select("div.article-title").text();

      headlineRepository.save(Headline.builder()
          .title(articleText.substring(0, articleText.indexOf('(')))
          .date(doc.select("div.source-date").text())
          .build());

      doc.select("div.image-article").stream()
          .map(element -> element.getElementsByClass("fancybox"))
          .flatMap(Collection::stream)
          .map(element -> element.attr("href"))
          .forEach(imageLink -> imageRepository.save(Image.builder().imageLink(imageLink).build()));

    } catch (Exception ex) {
      logger.error(ex.toString());
    }

  }

}
