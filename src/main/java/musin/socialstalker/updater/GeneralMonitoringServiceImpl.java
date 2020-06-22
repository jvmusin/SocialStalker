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
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.list.RelationList;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class GeneralMonitoringServiceImpl<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements GeneralMonitoringService<ID> {

  private final MonitoringRepository monitoringRepository;
  private final NetworkProperties properties;
  private final IdFactory<ID> idFactory;
  private final RelationListPuller<ID, TRelationList> relationListPuller;
  private final GeneralUpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> updateService;
  private final UpdateFactory<TUser, TRelationType, TUpdate> updateFactory;

  @Override
  @Transactional
  public List<ID> findAllTargets(Stalker stalker) {
    return monitoringRepository.findAllByStalkerAndNetwork(stalker, properties.getNetwork()).stream()
        .map(seeker -> idFactory.parse(seeker.getTarget()))
        .collect(toList());
  }

  @Override
  @Transactional
  public void addTarget(Stalker stalker, ID userId) {
    TRelationList list = relationListPuller.pull(userId).join();
    updateService.saveAll(stalker, list.asUpdates(updateFactory).collect(toList()), userId);
    monitoringRepository.save(new Monitoring(null, stalker, properties.getNetwork(), userId.toString()));
  }

  @Override
  @Transactional
  public void deleteTarget(Stalker stalker, ID userId) {
    updateService.removeAllByTarget(stalker, userId);
    monitoringRepository.deleteByStalkerAndTarget(stalker, userId.toString());
  }
}
