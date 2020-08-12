package musin.stalker.db.repository;

import musin.stalker.db.model.RelationUpdate;
import musin.stalker.db.model.Stalker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationUpdateRepository extends JpaRepository<RelationUpdate, Integer> {
  void deleteAllByStalkerAndTarget(Stalker stalker, String target);

  CompletableFuture<List<RelationUpdate>> findAllByStalkerAndNetworkAndTargetOrderById(Stalker stalker, String network, String target);
}
