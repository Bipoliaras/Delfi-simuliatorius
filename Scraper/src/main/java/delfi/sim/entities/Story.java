package delfi.sim.entities;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Story {

    private String headline;

    private List<Comment> comments;



}
