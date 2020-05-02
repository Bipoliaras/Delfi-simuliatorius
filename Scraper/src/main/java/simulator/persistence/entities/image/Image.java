package simulator.persistence.entities.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Entity(name = "t_image")
public class Image {

  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  @Column(name = "image_link", length = 16384)
  private String imageLink;

}
