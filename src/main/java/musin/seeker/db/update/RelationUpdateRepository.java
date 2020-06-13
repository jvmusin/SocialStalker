package musin.seeker.db.update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationUpdateRepository extends JpaRepository<RelationUpdate, Integer> {
  @Async
  CompletableFuture<List<RelationUpdate>> findAllByResourceAndOwner(String resource, String owner);
}
