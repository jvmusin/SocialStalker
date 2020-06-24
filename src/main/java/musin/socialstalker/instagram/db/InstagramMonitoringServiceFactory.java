package musin.socialstalker.instagram.db;

import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.updater.MonitoringServiceFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramMonitoringServiceFactory extends MonitoringServiceFactoryImpl<InstagramID> {
  public InstagramMonitoringServiceFactory(InstagramGeneralMonitoringService generalMonitoringService) {
    super(generalMonitoringService);
  }
}
