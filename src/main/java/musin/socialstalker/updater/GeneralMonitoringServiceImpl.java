package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.config.NetworkProperties;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.model.Monitoring;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.list.RelationList;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class GeneralMonitoringServiceImpl<
    ID,
    TRelationType,
    TRelationList extends RelationList<TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TRelationType>>
    implements GeneralMonitoringService<ID> {

  private final MonitoringRepository monitoringRepository;
  private final NetworkProperties properties;
  private final IdFactory<ID> idFactory;
  private final RelationListPuller<ID, TRelationType> relationListPuller;
  private final GeneralUpdateService<ID, TNotifiableUpdate, TRelationType> updateService;
  private final UpdateFactory<TRelationType, Update<TRelationType>> updateFactory;

  @Override
  @Transactional
  public List<ID> findAllTargets(Stalker stalker) {
    return monitoringRepository.findAllByStalkerAndNetwork(stalker, properties.getNetwork()).stream()
        .map(seeker -> idFactory.parse(seeker.getTarget()))
        .collect(toList());
  }

  @Override
  @Transactional
  public boolean exists(Stalker stalker, ID targetId) {
    return monitoringRepository.existsByStalkerAndNetworkAndTarget(stalker, properties.getNetwork(), targetId.toString());
  }

  @Override
  @Transactional
  public boolean createMonitoring(Stalker stalker, ID targetId) {
    if (exists(stalker, targetId)) return false;
    RelationList<TRelationType> list = relationListPuller.pull(targetId).join();
    monitoringRepository.save(new Monitoring(null, stalker, properties.getNetwork(), targetId.toString()));
    updateService.saveAll(stalker, list.asUpdates(updateFactory).collect(toList()), targetId);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteMonitoring(Stalker stalker, ID targetId) {
    if (!exists(stalker, targetId)) return false;
    updateService.removeAllByTarget(stalker, targetId);
    monitoringRepository.deleteByStalkerAndTarget(stalker, targetId.toString());
    return true;
  }
}
