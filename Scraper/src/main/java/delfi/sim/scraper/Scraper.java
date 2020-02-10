package delfi.sim.scraper;

import delfi.sim.entities.Comment;
import delfi.sim.entities.CommentRepository;
import delfi.sim.entities.Headline;
import delfi.sim.entities.HeadlineRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Scraper {

    private static final String WEBSITE = "https://www.delfi.lt/";

    private CommentRepository commentRepository;

    private HeadlineRepository headlineRepository;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(Scraper.class);

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setHeadlineRepository(HeadlineRepository headlineRepository) {
        this.headlineRepository = headlineRepository;
    }

    public void scrape() {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        WebDriver driver = new ChromeDriver();
        driver.get(WEBSITE);

        List<String> links = ((ChromeDriver) driver).findElementsByTagName("a").stream()
                .map(link -> link.getAttribute("href"))
                .filter(link -> link != null && link.contains("&com=1"))
                .collect(Collectors.toList());

        links.forEach(link -> scrapeNewsLink(link,driver));

        driver.close();
    }

    private void scrapeNewsLink(String link, WebDriver driver) {

        try {

            driver.get(link);

            //scrape article headline

            WebElement headline = ((ChromeDriver) driver).findElementByClassName("article-title");

            headlineRepository.save(Headline.builder().headlineText(headline.getText()).build());
            
            //scrape all anonymous comments
            List<Comment> comments = ((ChromeDriver) driver)
                .findElementsByClassName("comment-content")
                .stream()
                .map(WebElement::getText)
                .map(text -> Comment.builder().commentText(text).build())
                .collect(Collectors.toList());

            commentRepository.saveAll(comments);

        } catch (Exception ex) {
            logger.debug(ex.toString());
        }

    }

}
