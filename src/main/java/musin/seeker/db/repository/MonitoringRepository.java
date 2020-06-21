package musin.seeker.db.repository;

import musin.seeker.db.model.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoringRepository extends JpaRepository<Monitoring, Integer> {
}
