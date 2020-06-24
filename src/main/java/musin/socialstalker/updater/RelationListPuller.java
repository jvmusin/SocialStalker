package musin.socialstalker.updater;

import musin.socialstalker.api.Id;
import musin.socialstalker.relation.list.RelationList;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<ID extends Id> {
  CompletableFuture<RelationList> pull(ID userId);
}
