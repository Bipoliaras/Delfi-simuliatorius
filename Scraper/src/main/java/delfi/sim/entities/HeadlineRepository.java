package delfi.sim.entities;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadlineRepository extends JpaRepository<Headline, Long> {

  @Query(value = "SELECT * FROM t_headline ORDER BY random() LIMIT 1", nativeQuery = true)
  Headline findRandomHeadline();

}
