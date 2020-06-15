package musin.seeker.updater.v2;

import java.util.concurrent.CompletableFuture;

public interface RelationListPuller<
    ID,
    TRelationList> {

  CompletableFuture<TRelationList> pull(ID userId);
}
