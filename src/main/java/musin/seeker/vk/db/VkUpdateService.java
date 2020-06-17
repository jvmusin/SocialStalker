package musin.seeker.vk.db;

import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkNotifiableUpdateFactory;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkUpdate;
import musin.seeker.vk.relation.VkUser;
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
                         VkNotifiableUpdateFactory vkNotifiableUpdateFactory) {
    super(
        relationUpdateRepository,
        vkNotifiableUpdateFactory);
  }

  @Override
  protected VkRelationList createList() {
    return new VkRelationList();
  }

  @Override
  protected String getResource() {
    return VkConstants.RESOURCE;
  }
}
