package musin.seeker.db;

import musin.seeker.db.model.RelationChange;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RelationChangeService {
  List<RelationChange> findAll();
  CompletableFuture<List<RelationChange>> findAllByOwner(int owner);
  RelationChange save(RelationChange relationChange);
  List<RelationChange> saveAll(List<RelationChange> relationChange);
}