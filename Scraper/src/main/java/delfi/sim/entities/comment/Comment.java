package delfi.sim.entities.comment;

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
@Entity(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotEmpty
    @Column(name = "username", length = 16384)
    public String username;

    @NotEmpty
    @Column(name = "comment_text", length = 16384)
    public String text;

}
