import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Main {

  //btnLoadmore
  public static String websiteLink = "https://www.delfi.lt/news/daily/lithuania/feisbuke-plinta-nuotrauka-is-sventosios-parduotuves-atostogauja-taip-kad-praranda-nuovoka.d?id=81606817&com=1";


  public static void main(String args[]) {

    MongoClient mongoClient = new MongoClient();
    MongoDatabase localDatabase = mongoClient.getDatabase("local");
    MongoCollection<Document> commentCollection = localDatabase.getCollection("Comments");
    System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

    WebDriver driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


    ((ChromeDriver) driver).findElementByClassName("select-type-text").click();

    WebElement loadMoreCommentsButton = ((ChromeDriver) driver).findElementById("btnLoadmore");

    while (loadMoreCommentsButton != null) {
      loadMoreCommentsButton.click();

      loadMoreCommentsButton = ((ChromeDriver) driver).findElementById("btnLoadmore");
    }

    List<WebElement> comments = ((ChromeDriver) driver).findElementsByClassName("comment-content");

    for (WebElement comment : comments) {
      commentCollection.insertOne(new Document("comment", comment.getText()));
    }

  }
}
