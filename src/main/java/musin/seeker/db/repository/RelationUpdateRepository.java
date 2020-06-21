package musin.seeker.db.repository;

import musin.seeker.db.model.RelationUpdate;
import musin.seeker.db.model.Stalker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationUpdateRepository extends JpaRepository<RelationUpdate, Integer> {
  void deleteAllByStalkerAndTarget(Stalker stalker, String target);

  CompletableFuture<List<RelationUpdate>> findAllByNetworkAndTargetOrderById(String network, String target);
}
