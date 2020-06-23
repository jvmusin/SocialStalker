package musin.socialstalker.vk.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralUpdateService extends GeneralUpdateServiceImpl<
    VkID,
    VkRelationType
    > {

  public VkGeneralUpdateService(
      MonitoringRepository monitoringRepository,
      RelationUpdateRepository relationUpdateRepository,
      NotifiableUpdateFactory<VkRelationType> notifiableUpdateFactory,
      VkNetworkProperties networkProperties,
      RelationListFactory<RelationList<VkRelationType>> relationListFactory) {
    super(monitoringRepository, relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
