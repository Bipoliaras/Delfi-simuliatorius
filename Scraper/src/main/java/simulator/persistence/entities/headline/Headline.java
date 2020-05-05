package simulator.persistence.entities.headline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
