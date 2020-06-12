package musin.seeker.db;

import musin.seeker.db.model.RelationChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationChangeRepository extends JpaRepository<RelationChange, Integer> {
    List<RelationChange> findAllByOrderById();

    @Async
    CompletableFuture<List<RelationChange>> findAllByOwnerOrderById(int owner);
}