package musin.socialstalker.instagram.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.api.InstagramIdFactory;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.updater.InstagramRelationListPuller;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralMonitoringService extends GeneralMonitoringServiceImpl<InstagramID> {
  public InstagramGeneralMonitoringService(
      MonitoringRepository monitoringRepository,
      InstagramNetworkProperties properties,
      InstagramIdFactory idFactory,
      InstagramRelationListPuller relationListPuller,
      InstagramGeneralUpdateService updateService,
      UpdateFactory updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, updateService, updateFactory);
  }
}
