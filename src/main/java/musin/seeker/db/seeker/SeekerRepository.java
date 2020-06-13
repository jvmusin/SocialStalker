package musin.seeker.db.seeker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
  List<Seeker> findAllByResource(String resource);
}