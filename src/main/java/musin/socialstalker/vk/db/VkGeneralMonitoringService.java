package musin.socialstalker.vk.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.updater.VkRelationListPuller;
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
