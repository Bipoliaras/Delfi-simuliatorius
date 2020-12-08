package simulator.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simulator.persistence.entities.story.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

  @Query(value = "SELECT * FROM t_story ORDER BY random() LIMIT ?1", nativeQuery = true)
  List<Story> findRandomStories(Long limit);

}
