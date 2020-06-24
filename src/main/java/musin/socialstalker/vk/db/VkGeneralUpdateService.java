package musin.socialstalker.vk.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.notifier.VkNotifiableUpdateFactory;
import musin.socialstalker.vk.relation.VkRelationListFactory;
import org.springframework.stereotype.Component;

@Component
public class VkGeneralUpdateService extends GeneralUpdateServiceImpl {
  public VkGeneralUpdateService(MonitoringRepository monitoringRepository,
                                RelationUpdateRepository relationUpdateRepository,
                                VkNotifiableUpdateFactory notifiableUpdateFactory,
                                VkNetworkProperties networkProperties,
                                VkRelationListFactory relationListFactory) {
    super(monitoringRepository,
        relationUpdateRepository,
        notifiableUpdateFactory,
        networkProperties,
        relationListFactory);
  }
}
