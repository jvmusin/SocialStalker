package musin.socialstalker.instagram.db;

import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.RelationListPuller;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralMonitoringService extends GeneralMonitoringServiceImpl<InstagramID, InstagramRelationType> {
  public InstagramGeneralMonitoringService(
      MonitoringRepository monitoringRepository,
      InstagramNetworkProperties properties,
      IdFactory<InstagramID> idFactory,
      RelationListPuller<InstagramID, InstagramRelationType> relationListPuller,
      GeneralUpdateService<InstagramID, InstagramRelationType> updateService,
      UpdateFactory<InstagramRelationType> updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, updateService, updateFactory);
  }
}
