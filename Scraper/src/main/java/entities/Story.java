package scraper;

import entities.Comment;
import entities.Link;

import java.util.List;

public class Story {

    private String headline;

    private List<Comment> comments;

    private Link link;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }



}
