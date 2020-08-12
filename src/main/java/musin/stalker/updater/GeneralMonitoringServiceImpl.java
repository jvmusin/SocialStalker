package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.config.NetworkProperties;
import musin.stalker.db.IdFactory;
import musin.stalker.db.model.Monitoring;
import musin.stalker.db.model.Stalker;
import musin.stalker.db.repository.MonitoringRepository;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.relation.list.RelationList;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class GeneralMonitoringServiceImpl<ID extends Id> implements GeneralMonitoringService<ID> {

  private final MonitoringRepository monitoringRepository;
  private final NetworkProperties properties;
  private final IdFactory<ID> idFactory;
  private final RelationListPuller<ID> relationListPuller;
  private final GeneralUpdateService updateService;
  private final UpdateFactory updateFactory;

  @Override
  @Transactional
  public List<ID> findAllTargets(Stalker stalker) {
    return monitoringRepository.findAllByStalkerAndNetwork(stalker, properties.getNetwork()).stream()
        .map(monitoring -> idFactory.parse(monitoring.getTarget()))
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
    RelationList list = relationListPuller.pull(targetId).join();
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
