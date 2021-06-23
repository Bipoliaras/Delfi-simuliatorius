package simulator.domain.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import simulator.Constants.InterestType;

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

  public Story() {
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public InterestType getInterest() {
    return interest;
  }

  public void setInterest(InterestType interest) {
    this.interest = interest;
  }

  public String getImageSrc() {
    return imageSrc;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

}
