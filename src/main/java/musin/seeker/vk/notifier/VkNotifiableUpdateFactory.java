package musin.seeker.vk.notifier;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.notifier.NotifiableUpdateFactoryBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.config.VkServiceProperties;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkRelationTypeFactory;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component
public class VkNotifiableUpdateFactory extends NotifiableUpdateFactoryBase<
    VkID,
    VkUser,
    VkRelationType,
    VkNotifiableUpdate> {

  public VkNotifiableUpdateFactory(VkUserFactory userFactory,
                                   VkRelationTypeFactory relationTypeFactory,
                                   VkServiceProperties vkServiceProperties,
                                   VkIdFactory idFactory) {
    super(userFactory, relationTypeFactory, vkServiceProperties, idFactory);
  }

  @Override
  public VkNotifiableUpdate create(RelationUpdate relationUpdate) {
    return new VkNotifiableUpdateImpl(relationUpdate);
  }

  private class VkNotifiableUpdateImpl extends NotifiableUpdateBase implements VkNotifiableUpdate {
    protected VkNotifiableUpdateImpl(RelationUpdate update) {
      super(update);
    }
  }
}
