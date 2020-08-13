package musin.stalker.vk.db;

import musin.stalker.db.repository.MonitoringRepository;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.updater.GeneralMonitoringServiceImpl;
import musin.stalker.vk.api.VkID;
import musin.stalker.vk.api.VkIdFactory;
import musin.stalker.vk.config.VkNetworkProperties;
import musin.stalker.vk.updater.VkRelationListPuller;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralMonitoringService extends GeneralMonitoringServiceImpl<VkID> {
  public VkGeneralMonitoringService(MonitoringRepository monitoringRepository,
                                    VkNetworkProperties properties,
                                    VkIdFactory idFactory,
                                    VkRelationListPuller relationListPuller,
                                    VkGeneralUpdateService generalUpdateService,
                                    UpdateFactory updateFactory) {
    super(
        monitoringRepository,
        properties,
        idFactory,
        relationListPuller,
        generalUpdateService,
        updateFactory
    );
  }
}
