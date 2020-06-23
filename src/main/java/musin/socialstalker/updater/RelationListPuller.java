package musin.socialstalker.updater;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.list.RelationList;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<ID, TRelationType extends RelationType> {
  CompletableFuture<RelationList<RelationType>> pull(ID userId);
}
