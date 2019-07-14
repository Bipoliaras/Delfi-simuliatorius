import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.bson.Document;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

  private static Database db;

  public static final String websiteLink = "https://www.delfi.lt/";

  public static void main(String args[]) {

    db = new Database();
    db.init();

    System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

    WebDriver driver = new ChromeDriver();
    driver.get(websiteLink);

    List<String> links = ((ChromeDriver) driver).findElementsByTagName("a").stream()
        .map(link -> link.getAttribute("href"))
        .filter(link -> link != null && link.contains("&com=1"))
        .collect(Collectors.toList());

    links.forEach(link -> scrapeComments(link, driver));

    driver.close();
  }

  private static void scrapeComments(String link, WebDriver driver) {

    driver.get(link);

   // ((ChromeDriver) driver).findElementByClassName("select-type-text").click();

//    WebElement loadMoreCommentsButton = ((ChromeDriver) driver).findElementById("btnLoadmore");
//
//    while (loadMoreCommentsButton != null) {
//      loadMoreCommentsButton.click();
//
//      loadMoreCommentsButton = ((ChromeDriver) driver).findElementById("btnLoadmore");
//    }

    List<String> comments = ((ChromeDriver) driver).findElementsByClassName("comment-content")
          .stream()
          .map(comment -> comment.getText())
          .collect(Collectors.toList());

    comments.forEach(comment -> db.saveComment(comment));

  }

}
