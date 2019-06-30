package musin.seeker.vkseeker.db;

import musin.seeker.vkseeker.db.model.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
}