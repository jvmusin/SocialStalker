package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.updater.GeneralMonitoringService;
import musin.socialstalker.updater.MonitoringService;
import musin.socialstalker.updater.MonitoringServiceFactory;
import musin.socialstalker.updater.MonitoringServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramMonitoringServiceFactory implements MonitoringServiceFactory<InstagramID> {

  private final GeneralMonitoringService<InstagramID> generalMonitoringService;

  @Override
  public MonitoringService<InstagramID> create(Stalker stalker) {
    return new MonitoringServiceImpl<>(stalker, generalMonitoringService);
  }
}
