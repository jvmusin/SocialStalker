package musin.stalker.vk.db;

import musin.stalker.db.repository.MonitoringRepository;
import musin.stalker.db.repository.RelationUpdateRepository;
import musin.stalker.updater.GeneralUpdateServiceImpl;
import musin.stalker.vk.config.VkNetworkProperties;
import musin.stalker.vk.notifier.VkNotifiableUpdateFactory;
import musin.stalker.vk.relation.VkRelationListFactory;
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
