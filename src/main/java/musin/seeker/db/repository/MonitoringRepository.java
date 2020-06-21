package musin.seeker.db.repository;

import musin.seeker.db.model.Monitoring;
import musin.seeker.db.model.Stalker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringRepository extends JpaRepository<Monitoring, Integer> {
  List<Monitoring> findAllByStalkerAndNetwork(Stalker stalker, String network);

  void deleteByStalkerAndTarget(Stalker stalker, String target);
}
