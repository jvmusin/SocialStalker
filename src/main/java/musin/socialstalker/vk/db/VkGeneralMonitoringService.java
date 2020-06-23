package musin.socialstalker.vk.db;

import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.RelationListPuller;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralMonitoringService extends GeneralMonitoringServiceImpl<VkID, VkRelationType> {
  public VkGeneralMonitoringService(MonitoringRepository monitoringRepository,
                                    VkNetworkProperties properties,
                                    IdFactory<VkID> idFactory,
                                    RelationListPuller<VkID, VkRelationType> relationListPuller,
                                    GeneralUpdateService<VkID, VkRelationType> generalUpdateService,
                                    UpdateFactory<VkRelationType> updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, generalUpdateService, updateFactory);
  }
}
