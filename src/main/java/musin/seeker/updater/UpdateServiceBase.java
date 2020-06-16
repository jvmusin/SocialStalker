package musin.seeker.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.Update;
import musin.seeker.relation.User;
import musin.seeker.relation.UserFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class UpdateServiceBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList,
    TNotifiableUpdate>
    implements UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final UserFactory<ID, TUser> userFactory;

  @Override
  public List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(update -> updateToRelationUpdate(update, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(this::createNotifiableUpdate)
        .collect(toList());
  }

  public CompletableFuture<TRelationList> buildList(ID owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(getResource(), owner.toString())
        .thenApply(r -> r.stream().map(this::createNotifiableUpdate).collect(toList()))
        .thenApply(this::createList);
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

  protected abstract TNotifiableUpdate createNotifiableUpdate(RelationUpdate update);

  protected abstract TRelationList createList(List<TNotifiableUpdate> updates);

  protected abstract String getResource();

  @Data
  protected abstract class NotifiableUpdateBase implements NotifiableUpdate<TUser, TRelationType> {
    private final Integer id;
    private final TUser owner;
    private final TUser target;
    private final TRelationType was;
    private final TRelationType now;
    private final LocalDateTime time;

    protected NotifiableUpdateBase(RelationUpdate update) {
      id = update.getId();
      owner = userFactory.create(parseId(update.getOwner()));
      target = userFactory.create(parseId(update.getTarget()));
      was = parseRelationType(update.getWas());
      now = parseRelationType(update.getNow());
      time = update.getTime();
    }

    protected abstract ID parseId(String id);

    protected abstract TRelationType parseRelationType(String type);

    @Override
    public String getResource() {
      return UpdateServiceBase.this.getResource();
    }
  }
}
