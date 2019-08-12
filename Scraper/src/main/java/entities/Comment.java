package scraper;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Comment {

    @Id
    public ObjectId _id;

    public String comment;

    public Comment() {}

    public Comment(String comment) {
        this.comment = comment;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
