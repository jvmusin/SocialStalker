package musin.stalker.updater;

import musin.stalker.db.Id;
import musin.stalker.relation.list.RelationList;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<ID extends Id> {
  CompletableFuture<RelationList> pull(ID userId);
}
