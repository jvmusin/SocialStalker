package musin.socialstalker.vk.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.notifier.VkNotifiableUpdateFactory;
import musin.socialstalker.vk.relation.VkRelationListFactory;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralUpdateService extends GeneralUpdateServiceImpl<VkID, RelationType> {
  public VkGeneralUpdateService(MonitoringRepository monitoringRepository,
                                RelationUpdateRepository relationUpdateRepository,
                                VkNotifiableUpdateFactory notifiableUpdateFactory,
                                VkNetworkProperties networkProperties,
                                VkRelationListFactory relationListFactory) {
    super(monitoringRepository, relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
