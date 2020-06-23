package musin.socialstalker.vk.db;

import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.GeneralMonitoringServiceImpl;
import musin.socialstalker.updater.RelationListPuller;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.notifier.VkNotifiableUpdate;
import musin.socialstalker.vk.relation.VkRelationList;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkUpdate;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralMonitoringService extends GeneralMonitoringServiceImpl<
    VkID,
    VkRelationType,
    VkUpdate,
    VkRelationList,
    VkNotifiableUpdate> {

  public VkGeneralMonitoringService(MonitoringRepository monitoringRepository,
                                    VkNetworkProperties properties,
                                    IdFactory<VkID> idFactory,
                                    RelationListPuller<VkID, VkRelationType> relationListPuller,
                                    VkGeneralUpdateService generalUpdateService,
                                    UpdateFactory<VkRelationType, VkUpdate> updateFactory) {
    super(monitoringRepository, properties, idFactory, relationListPuller, generalUpdateService, updateFactory);
  }
}
