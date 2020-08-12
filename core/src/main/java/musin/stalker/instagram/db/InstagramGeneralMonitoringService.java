package musin.stalker.instagram.db;

import musin.stalker.db.repository.MonitoringRepository;
import musin.stalker.instagram.api.InstagramID;
import musin.stalker.instagram.api.InstagramIdFactory;
import musin.stalker.instagram.config.InstagramNetworkProperties;
import musin.stalker.instagram.updater.InstagramRelationListPuller;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.updater.GeneralMonitoringServiceImpl;
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
