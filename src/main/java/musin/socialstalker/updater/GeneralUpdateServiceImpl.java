package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import musin.socialstalker.config.NetworkProperties;
import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Log4j2
public class GeneralUpdateServiceImpl<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements GeneralUpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  private final MonitoringRepository monitoringRepository;
  private final RelationUpdateRepository relationUpdateRepository;
  private final NotifiableUpdateFactory<TUser, TRelationType, TNotifiableUpdate> notifiableUpdateFactory;
  private final NetworkProperties networkProperties;
  private final RelationListFactory<TRelationList> relationListFactory;

  @Override
  @Transactional
  public List<TNotifiableUpdate> saveAll(Stalker stalker, List<? extends TUpdate> updates, ID target) {
    if (updates.isEmpty()) return emptyList();
    if (monitoringRepository.existsByStalkerAndNetworkAndTarget(stalker, networkProperties.getNetwork(), target.toString())) {
      List<RelationUpdate> relationUpdates = updates.stream()
          .map(update -> updateToRelationUpdate(stalker, update, target))
          .collect(toList());
      return relationUpdateRepository.saveAll(relationUpdates).stream()
          .map(notifiableUpdateFactory::create)
          .collect(toList());
    } else {
      log.warn(
          "Unable to save {} updates for the stalker {}, network {} and target {} because such monitoring doesn't exist." +
              "This may happen when the monitoring is deleted in the middle of update",
          updates.size(), stalker, networkProperties.getNetwork(), target);
      return emptyList();
    }
  }

  private RelationUpdate updateToRelationUpdate(Stalker stalker, TUpdate update, ID target) {
    return RelationUpdate.builder()
        .stalker(stalker)
        .network(networkProperties.getNetwork())
        .target(target.toString())
        .suspected(update.getSuspected().getId().toString())
        .was(update.getWas() == null ? null : update.getWas().toString())
        .now(update.getNow() == null ? null : update.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  @Override
  @Transactional
  public void removeAllByTarget(Stalker stalker, ID target) {
    relationUpdateRepository.deleteAllByStalkerAndTarget(stalker, target.toString());
  }

  @Override
  @Transactional
  public CompletableFuture<TRelationList> buildList(Stalker stalker, ID target) {
    return relationUpdateRepository.findAllByNetworkAndTargetOrderById(networkProperties.getNetwork(), target.toString())
        .thenApply(r -> r.stream().map(notifiableUpdateFactory::create))
        .thenApply(this::createList);
  }

  private TRelationList createList(Stream<? extends Update<? extends TUser, ? extends TRelationType>> updates) {
    TRelationList list = relationListFactory.create();
    updates.forEach(list::apply);
    return list;
  }
}