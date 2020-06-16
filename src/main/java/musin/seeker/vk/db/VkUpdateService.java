package musin.seeker.vk.db;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.*;
import org.springframework.stereotype.Service;

@Service
public class VkUpdateService extends UpdateServiceBase<VkID, VkUser, VkRelationType, VkUpdate, VkRelationList, VkNotifiableUpdate> {

  public VkUpdateService(RelationUpdateRepository relationUpdateRepository, VkUserFactory userFactory, VkRelationTypeFactory vkRelationTypeFactory) {
    super(relationUpdateRepository, userFactory, vkRelationTypeFactory);
  }

  @Override
  protected VkNotifiableUpdate createNotifiableUpdate(RelationUpdate update) {
    return new VkNotifiableUpdateImpl(update);
  }

  @Override
  protected VkRelationList createList() {
    return new VkRelationList();
  }

  @Override
  protected String getResource() {
    return VkConstants.RESOURCE;
  }

  private class VkNotifiableUpdateImpl extends NotifiableUpdateBase implements VkNotifiableUpdate {
    protected VkNotifiableUpdateImpl(RelationUpdate update) {
      super(update);
    }

    @Override
    protected VkID parseId(String id) {
      return new VkID(id);
    }
  }
}
