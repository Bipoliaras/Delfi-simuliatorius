package simulator.service;

import java.util.List;
import org.springframework.stereotype.Service;
import simulator.domain.entity.Comment;
import simulator.repository.CommentRepository;

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
