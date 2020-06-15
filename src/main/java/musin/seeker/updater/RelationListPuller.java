package musin.seeker.updater;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<ID, TRelationList> {
  CompletableFuture<TRelationList> pull(ID userId);
}
