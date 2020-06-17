package musin.seeker.vk.relation;

import musin.seeker.relation.RelationFactoryBase;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory extends RelationFactoryBase<VkID, VkUser, VkRelationType, VkRelation> {
  public VkRelationFactory(VkUserFactory userFactory) {
    super(userFactory);
  }

  @Override
  public VkRelation createByUser(VkUser user, VkRelationType type) {
    return new VkRelation(user, type);
  }
}
