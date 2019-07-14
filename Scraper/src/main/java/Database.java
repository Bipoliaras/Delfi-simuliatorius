import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.openqa.selenium.WebElement;

public class Database {

    private static MongoClient mongoClient;
    private static MongoDatabase db;

    private static final String commentCollectionName = "Comments";

    public static void init() {
        connectToDatabase();
        initializeCommentCollection();
    }

    private static void connectToDatabase() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase("local");
    }

    private static void initializeCommentCollection() {
        if(!collectionExists(commentCollectionName)) {
            db.createCollection(commentCollectionName);
        }
    }

  private static boolean collectionExists(String collectionName) {
    MongoIterable<String> collectionNames = db.listCollectionNames();
    try (final MongoCursor<String> it = collectionNames.iterator()) {
      while (it.hasNext()) {
        if (it.next().equalsIgnoreCase(collectionName)) {
          return true;
        }
      }
    }
    return false;
  }

    public void saveComment(String comment) {
        db.getCollection("Comments").insertOne(new Document().append("comment",
            comment));
    }

}
