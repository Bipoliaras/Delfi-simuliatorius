package simulator.comments;

import java.util.List;
import simulator.persistence.entities.comment.Comment;

public class CommentResponse {

  public CommentResponse(List<Comment> comments) {
    this.comments = comments;
  }

  private List<Comment> comments;


  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
