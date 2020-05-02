package simulator.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simulator.persistence.entities.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query(value = "SELECT * FROM t_comment ORDER BY random() LIMIT ?1", nativeQuery = true)
  List<Comment> findRandomComments(Integer limit);

}
