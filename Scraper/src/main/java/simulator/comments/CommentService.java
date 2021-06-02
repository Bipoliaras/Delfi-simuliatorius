package simulator.comments;

import java.util.List;
import org.springframework.stereotype.Service;
import simulator.persistence.entities.comment.Comment;
import simulator.persistence.repositories.CommentRepository;

@Service
public class CommentService {

  private final CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public List<Comment> getRandomComments(int limit) {
    return commentRepository.findRandomComments(limit);
  }


}
