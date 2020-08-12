package musin.stalker.instagram.db;

import musin.stalker.instagram.api.InstagramID;
import musin.stalker.updater.MonitoringServiceFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramMonitoringServiceFactory extends MonitoringServiceFactoryImpl<InstagramID> {
  public InstagramMonitoringServiceFactory(InstagramGeneralMonitoringService generalMonitoringService) {
    super(generalMonitoringService);
  }
}
