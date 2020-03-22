package delfi.sim.entities.story;
import delfi.sim.entities.comment.Comment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    private String title;
    private String createdOn;
    private String imageSrc;
    private InterestType interest;
    private List<Comment> comments;

}
