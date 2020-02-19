package delfi.sim.entities;
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

    private Headline headline;

    private Image image;

    private List<Comment> comments;

}
