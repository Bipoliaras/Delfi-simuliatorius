package simulator.persistence.entities.headline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


@Entity(name = "t_headline")
public class Headline {

  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  @Column(name = "title", length = 16384)
  private String title;

  @NotEmpty
  @Column(name = "date", length = 16384)
  private String date;

  public Headline() { }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Headline(String title, String date) {
    this.title = title;
    this.date = date;
  }
}
