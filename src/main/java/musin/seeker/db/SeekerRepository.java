package musin.seeker.db;

import musin.seeker.db.model.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
}