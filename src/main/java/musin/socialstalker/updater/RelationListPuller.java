package musin.socialstalker.updater;

import musin.socialstalker.relation.list.RelationList;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<ID> {
  CompletableFuture<RelationList> pull(ID userId);
}
