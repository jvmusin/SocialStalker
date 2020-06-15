package musin.seeker.updater;

import lombok.Data;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.Update;
import musin.seeker.relation.User;

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

  @Data
  protected abstract class NotifiableUpdateBase<TUser extends User<?>, TRelationType> implements NotifiableUpdate<TUser, TRelationType> {
    private final Integer id;
    private final TUser owner;
    private final TUser target;
    private final TRelationType was;
    private final TRelationType now;
    private final LocalDateTime time;

    protected NotifiableUpdateBase(RelationUpdate update) {
      id = update.getId();
      owner = createUser(parseId(update.getOwner()));
      target = createUser(parseId(update.getTarget()));
      was = parseRelationType(update.getWas());
      now = parseRelationType(update.getNow());
      time = update.getTime();
    }

    protected abstract TUser createUser(ID id);

    protected abstract ID parseId(String id);

    protected abstract TRelationType parseRelationType(String type);
  }
}
