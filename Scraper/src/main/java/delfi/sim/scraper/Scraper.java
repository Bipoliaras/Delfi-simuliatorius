package delfi.sim.scraper;

import delfi.sim.comments.CommentEndpoint;
import delfi.sim.comments.CommentTypes;
import delfi.sim.entities.comment.CommentRepository;
import delfi.sim.entities.headline.Headline;
import delfi.sim.entities.headline.HeadlineRepository;
import delfi.sim.entities.image.Image;
import delfi.sim.entities.image.ImageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Scraper {

  private HeadlineRepository headlineRepository;

  private ImageRepository imageRepository;

  private CommentRepository commentRepository;

  private CommentEndpoint commentEndpoint;

  private Logger logger = LoggerFactory.getLogger(Scraper.class);

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

  @Autowired
  public void setCommentRepository(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public void scrape() {

    for (WebsiteLinks websiteLink : WebsiteLinks.values()) {
      scrapeLink(websiteLink.getUrl());
    }
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
