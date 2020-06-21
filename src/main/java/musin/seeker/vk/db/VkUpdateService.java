package musin.seeker.vk.db;

import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.config.VkNetworkProperties;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkNotifiableUpdateFactory;
import musin.seeker.vk.relation.*;
import org.springframework.stereotype.Service;

@Service
public class VkUpdateService extends UpdateServiceBase<
    VkID,
    VkUser,
    VkRelationType,
    VkUpdate,
    VkRelationList,
    VkNotifiableUpdate> {

  public VkUpdateService(RelationUpdateRepository relationUpdateRepository,
                         VkNotifiableUpdateFactory notifiableUpdateFactory,
                         VkNetworkProperties networkProperties,
                         VkRelationListFactory relationListFactory) {
    super(
        relationUpdateRepository,
        notifiableUpdateFactory,
        networkProperties,
        relationListFactory);
  }
}
