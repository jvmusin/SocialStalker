package musin.socialstalker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.updater.MonitoringService;
import musin.socialstalker.updater.MonitoringServiceFactory;
import musin.socialstalker.updater.MonitoringServiceImpl;
import musin.socialstalker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkMonitoringServiceFactory implements MonitoringServiceFactory<VkID> {

  private final VkGeneralMonitoringService generalMonitoringService;

  @Override
  public MonitoringService<VkID> create(Stalker stalker) {
    return new MonitoringServiceImpl<>(stalker, generalMonitoringService);
  }
}
