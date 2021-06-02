package simulator.persistence.entities.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity(name = "t_image")
public class Image {

  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  @Column(name = "image_link", length = 16384)
  private String imageLink;

  public Image() { }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Image(String imageLink) {
    this.imageLink = imageLink;
  }
}
