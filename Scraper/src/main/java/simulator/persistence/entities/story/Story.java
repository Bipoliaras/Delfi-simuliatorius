package simulator.persistence.entities.story;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import simulator.persistence.entities.comment.Comment;

@Entity(name = "t_story")
public class Story {

  @Id
  @GeneratedValue
  private Long id;

  private String title;
  private String createdOn;
  private String imageSrc;

  @Enumerated(EnumType.STRING)
  private InterestType interest;

  @ManyToMany
  private List<Comment> comments;

  public Story(String title, String createdOn, String imageSrc,
      InterestType interest,
      List<Comment> comments) {
    this.title = title;
    this.createdOn = createdOn;
    this.imageSrc = imageSrc;
    this.interest = interest;
    this.comments = comments;
  }

  public Story() { }
}
