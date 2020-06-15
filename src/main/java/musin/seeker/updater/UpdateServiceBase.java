package musin.seeker.updater;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.relation.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class UpdateServiceBase<
    ID,
    TUpdate extends Update<?, ?>,
    TRelationList,
    TNotifiableUpdate>
    implements UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  public CompletableFuture<TRelationList> buildList(ID owner) {
    return findAllByOwner(owner).thenApply(this::createList);
  }

  protected RelationUpdate updateToRelationUpdate(TUpdate update, ID owner) {
    return RelationUpdate.builder()
        .resource(getResource())
        .owner(owner.toString())
        .target(update.getTarget().getId().toString())
        .was(update.getWas() == null ? null : update.getWas().toString())
        .now(update.getNow() == null ? null : update.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  protected abstract TRelationList createList(List<TNotifiableUpdate> updates);

  protected abstract String getResource();
}
