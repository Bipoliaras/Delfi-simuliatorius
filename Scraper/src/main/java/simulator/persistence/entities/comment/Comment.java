package simulator.persistence.entities.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


@Entity(name = "t_comment")
public class Comment {

  @Id
  @GeneratedValue
  public Long id;

  @NotEmpty
  @Column(name = "username", length = 16384)
  public String username;

  @NotEmpty
  @Column(name = "comment_text", length = 16384)
  public String text;

  public Comment() { }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Comment(String username, String text) {
    this.username = username;
    this.text = text;
  }

}
