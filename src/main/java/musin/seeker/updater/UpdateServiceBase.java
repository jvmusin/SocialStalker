package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.ServiceProperties;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.notifier.NotifiableUpdateFactory;
import musin.seeker.relation.list.RelationList;
import musin.seeker.relation.list.RelationListFactory;
import musin.seeker.relation.Update;
import musin.seeker.relation.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class UpdateServiceBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final NotifiableUpdateFactory<TUser, TRelationType, TNotifiableUpdate> notifiableUpdateFactory;
  private final ServiceProperties serviceProperties;
  private final RelationListFactory<TRelationList> relationListFactory;

  @Override
  public List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(update -> updateToRelationUpdate(update, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(notifiableUpdateFactory::create)
        .collect(toList());
  }

  public CompletableFuture<TRelationList> buildList(ID owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(serviceProperties.getResource(), owner.toString())
        .thenApply(r -> r.stream().map(notifiableUpdateFactory::create))
        .thenApply(this::createList);
  }

  protected RelationUpdate updateToRelationUpdate(TUpdate update, ID owner) {
    return RelationUpdate.builder()
        .resource(serviceProperties.getResource())
        .owner(owner.toString())
        .target(update.getTarget().getId().toString())
        .was(update.getWas() == null ? null : update.getWas().toString())
        .now(update.getNow() == null ? null : update.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  private TRelationList createList(Stream<? extends Update<? extends TUser, ? extends TRelationType>> updates) {
    TRelationList list = relationListFactory.create();
    updates.forEach(list::apply);
    return list;
  }
}
