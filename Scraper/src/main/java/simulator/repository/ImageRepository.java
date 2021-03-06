package simulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import simulator.domain.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

  @Query(value = "SELECT * FROM t_image ORDER BY random() LIMIT 1", nativeQuery = true)
  Image findRandomImage();

}
