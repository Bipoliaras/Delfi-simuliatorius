package scraper;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Headline {
    @Id
    private ObjectId _id;

    private String headline;

    public Headline() {}

    public Headline(String headline) {
        this.headline = headline;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String comment) {
        this.headline = comment;
    }

}
