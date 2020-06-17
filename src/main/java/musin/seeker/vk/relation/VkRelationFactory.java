package musin.seeker.vk.relation;

import musin.seeker.relation.RelationFactoryBase;
import musin.seeker.relation.UserFactory;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory extends RelationFactoryBase<VkID, VkUser, VkRelationType, VkRelation> {
  public VkRelationFactory(UserFactory<VkID, VkUser> vkIDVkUserUserFactory) {
    super(vkIDVkUserUserFactory);
  }

  @Override
  public VkRelation create(VkID id, VkRelationType type) {
    return new VkRelation(createUser(id), type);
  }
}
