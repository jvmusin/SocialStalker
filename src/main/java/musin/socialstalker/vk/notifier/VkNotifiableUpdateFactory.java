package musin.socialstalker.vk.notifier;

import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.notifier.NotifiableUpdateFactoryBase;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkRelationTypeFactory;
import musin.socialstalker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component
public class VkNotifiableUpdateFactory extends NotifiableUpdateFactoryBase<VkID, RelationType> {

  public VkNotifiableUpdateFactory(VkUserFactory userFactory,
                                   VkRelationTypeFactory relationTypeFactory,
                                   VkNetworkProperties networkProperties,
                                   VkIdFactory idFactory) {
    super(userFactory, relationTypeFactory, networkProperties, idFactory);
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
