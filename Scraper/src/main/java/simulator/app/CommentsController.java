package simulator.app;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import simulator.comments.CommentResponse;
import simulator.comments.CommentService;
import simulator.persistence.entities.comment.Comment;

@RestController
@RequestMapping("/comments")
public class CommentsController {

  private final CommentService commentService;

  public CommentsController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public CommentResponse getComments(@RequestParam(required = false, defaultValue = "1") int limit) {
    List<Comment> comments = commentService.getRandomComments(limit);
    return new CommentResponse(comments);
  }

}
