package musin.seeker.vk.db;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.relation.UserFactory;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkUpdate;
import musin.seeker.vk.relation.VkUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkUpdateService extends UpdateServiceBase<VkID, VkUser, VkRelationType, VkUpdate, VkRelationList, VkNotifiableUpdate> {

  public VkUpdateService(RelationUpdateRepository relationUpdateRepository, UserFactory<VkID, VkUser> userFactory) {
    super(relationUpdateRepository, userFactory);
  }

  @Override
  protected VkNotifiableUpdate createNotifiableUpdate(RelationUpdate update) {
    return new VkNotifiableUpdateImpl(update);
  }

  @Override
  protected VkRelationList createList(List<VkNotifiableUpdate> updates) {
    return new VkRelationList(updates);
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

    @Override
    protected VkRelationType parseRelationType(String type) {
      return VkRelationType.parseNullSafe(type);
    }
  }
}
