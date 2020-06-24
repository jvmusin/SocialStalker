package musin.socialstalker.vk.db;

import musin.socialstalker.updater.MonitoringServiceFactoryImpl;
import musin.socialstalker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
public class VkMonitoringServiceFactory extends MonitoringServiceFactoryImpl<VkID> {
  public VkMonitoringServiceFactory(VkGeneralMonitoringService generalMonitoringService) {
    super(generalMonitoringService);
  }
}
