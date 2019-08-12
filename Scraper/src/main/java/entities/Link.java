package entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Link {

    @Id
    private ObjectId _id;

    private String link;


    public Link(String link) {
        this.link = link;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
