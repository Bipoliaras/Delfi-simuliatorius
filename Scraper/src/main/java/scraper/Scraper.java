package scraper;

import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import org.openqa.selenium.chrome.ChromeOptions;
import repositories.CommentRepository;
import repositories.HeadlineRepository;

public class Main {

    public Main(CommentRepository commentRepository, HeadlineRepository headlineRepository, LinkRepository linkRepository) {
        this.commentRepository = commentRepository;
        this.headlineRepository = headlineRepository;
        this.linkRepository = linkRepository;
    }

    public static final String websiteLink = "https://www.delfi.lt/";

    private CommentRepository commentRepository;

    private HeadlineRepository headlineRepository;

    public void mainAction() {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        options.setHeadless(true);

        WebDriver driver = new ChromeDriver();
        driver.get(websiteLink);

        List<String> links = ((ChromeDriver) driver).findElementsByTagName("a").stream()
                .map(link -> link.getAttribute("href"))
                .filter(link -> link != null && link.contains("&com=1"))
                .collect(Collectors.toList());

        links.forEach(link -> scrapeNewsLink(link, driver));

        driver.close();
    }

    private void scrapeNewsLink(String link, WebDriver driver) {

        driver.get(link);


        //scrape article headline

        WebElement headline = ((ChromeDriver) driver).findElementByClassName("article-title");
        commentRepository.save(new Headline(headline.getText()));


        //scrape all anonymous comments
        List<String> comments = ((ChromeDriver) driver).findElementsByClassName("comment-content")
                .stream()
                .map(comment -> comment.getText().replaceAll("[^a-zA-ZąĄčČęĘėĖįĮšŠųŲūŪžŽ0-9\\s+]"," "))
                .collect(Collectors.toList());

        comments.forEach(comment -> commentRepository.save(new Comment(comment)));

    }

}
