package simulator.persistence.entities.story;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import simulator.persistence.entities.comment.Comment;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
