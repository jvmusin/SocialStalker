package musin.socialstalker.db.repository;

import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.db.model.Stalker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationUpdateRepository extends JpaRepository<RelationUpdate, Integer> {
  void deleteAllByStalkerAndTarget(Stalker stalker, String target);

  CompletableFuture<List<RelationUpdate>> findAllByStalkerAndNetworkAndTargetOrderById(Stalker stalker, String network, String target);
}
