package musin.socialstalker.vk.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.notifier.VkNotifiableUpdate;
import musin.socialstalker.vk.relation.VkRelationList;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralUpdateService extends GeneralUpdateServiceImpl<
    VkID,
    VkRelationType,
    VkRelationList,
    VkNotifiableUpdate> {

  public VkGeneralUpdateService(
      MonitoringRepository monitoringRepository,
      RelationUpdateRepository relationUpdateRepository,
      NotifiableUpdateFactory<VkRelationType, VkNotifiableUpdate> notifiableUpdateFactory,
      VkNetworkProperties networkProperties,
      RelationListFactory<VkRelationList> relationListFactory) {
    super(monitoringRepository, relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
