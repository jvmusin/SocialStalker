package musin.socialstalker.instagram.db;

import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramRelationList;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.RelationListPuller;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralMonitoringService extends GeneralMonitoringServiceImpl<
    InstagramID,
    InstagramRelationType,
    InstagramUpdate,
    InstagramRelationList,
    InstagramNotifiableUpdate> {

  public InstagramGeneralMonitoringService(
      MonitoringRepository monitoringRepository,
      InstagramNetworkProperties properties,
      IdFactory<InstagramID> idFactory,
      RelationListPuller<InstagramID, InstagramRelationType> relationListPuller,
      GeneralUpdateService<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> updateService,
      UpdateFactory<InstagramRelationType, InstagramUpdate> updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, updateService, updateFactory);
  }
}
