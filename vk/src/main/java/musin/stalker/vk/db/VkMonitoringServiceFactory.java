package musin.stalker.vk.db;

import musin.stalker.updater.MonitoringServiceFactoryImpl;
import musin.stalker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
public class VkMonitoringServiceFactory extends MonitoringServiceFactoryImpl<VkID> {
  public VkMonitoringServiceFactory(VkGeneralMonitoringService generalMonitoringService) {
    super(generalMonitoringService);
  }
}
