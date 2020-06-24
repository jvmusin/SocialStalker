package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musin.socialstalker.api.Id;
import musin.socialstalker.config.NetworkProperties;
import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.Update;
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
@Slf4j
public class GeneralUpdateServiceImpl implements GeneralUpdateService {

  private final MonitoringRepository monitoringRepository;
  private final RelationUpdateRepository relationUpdateRepository;
  private final NotifiableUpdateFactory notifiableUpdateFactory;
  private final NetworkProperties networkProperties;
  private final RelationListFactory relationListFactory;

  @Override
  @Transactional
  public List<NotifiableUpdate> saveAll(Stalker stalker, List<Update> updates, Id target) {
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

  private RelationUpdate updateToRelationUpdate(Stalker stalker, Update update, Id target) {
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
  public void removeAllByTarget(Stalker stalker, Id target) {
    relationUpdateRepository.deleteAllByStalkerAndTarget(stalker, target.toString());
  }

  @Override
  @Transactional
  public CompletableFuture<RelationList> buildList(Stalker stalker, Id target) {
    return relationUpdateRepository.findAllByStalkerAndNetworkAndTargetOrderById(stalker, networkProperties.getNetwork(), target.toString())
        .thenApply(r -> r.stream().map(notifiableUpdateFactory::create))
        .thenApply(this::createList);
  }

  private RelationList createList(Stream<NotifiableUpdate> updates) {
    RelationList list = relationListFactory.create();
    updates.forEach(list::apply);
    return list;
  }
}
