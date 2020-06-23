package musin.socialstalker.vk.db;

import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.RelationListPuller;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkUpdateFactory;
import musin.socialstalker.vk.updater.VkRelationListPuller;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralMonitoringService extends GeneralMonitoringServiceImpl<VkID, RelationType> {
  public VkGeneralMonitoringService(MonitoringRepository monitoringRepository,
                                    VkNetworkProperties properties,
                                    VkIdFactory idFactory,
                                    VkRelationListPuller relationListPuller,
                                    VkGeneralUpdateService generalUpdateService,
                                    VkUpdateFactory updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, generalUpdateService, updateFactory);
  }
}
