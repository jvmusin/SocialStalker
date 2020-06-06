package musin.seeker.vkseeker.db;

import musin.seeker.vkseeker.db.model.RelationChange;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationChangeService {
    List<RelationChange> findAll();
    List<RelationChange> findAllByOwner(int owner);
    CompletableFuture<List<RelationChange>> findAllByOwnerAsync(int owner);
    RelationChange save(RelationChange relationChange);
    List<RelationChange> saveAll(List<RelationChange> relationChange);
}