package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.NetworkProperties;
import musin.seeker.db.model.RelationUpdate;
import musin.seeker.db.model.Stalker;
import musin.seeker.db.repository.RelationUpdateRepository;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.notifier.NotifiableUpdateFactory;
import musin.seeker.relation.Update;
import musin.seeker.relation.User;
import musin.seeker.relation.list.RelationList;
import musin.seeker.relation.list.RelationListFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class UpdateServiceBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  private final Stalker stalker;
  private final RelationUpdateRepository relationUpdateRepository;
  private final NotifiableUpdateFactory<TUser, TRelationType, TNotifiableUpdate> notifiableUpdateFactory;
  private final NetworkProperties networkProperties;
  private final RelationListFactory<TRelationList> relationListFactory;

  @Override
  public List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID target) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(update -> updateToRelationUpdate(update, target))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(notifiableUpdateFactory::create)
        .collect(toList());
  }

  @Override
  public void removeAllByTarget(ID target) {
    relationUpdateRepository.deleteAllByStalkerAndTarget(stalker, target.toString());
  }

  public CompletableFuture<TRelationList> buildList(ID target) {
    return relationUpdateRepository.findAllByNetworkAndTargetOrderById(networkProperties.getNetwork(), target.toString())
        .thenApply(r -> r.stream().map(notifiableUpdateFactory::create))
        .thenApply(this::createList);
  }

  protected RelationUpdate updateToRelationUpdate(TUpdate update, ID target) {
    return RelationUpdate.builder()
        .network(networkProperties.getNetwork())
        .target(target.toString())
        .suspected(update.getSuspected().getId().toString())
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
