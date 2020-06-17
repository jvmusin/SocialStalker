package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.relation.RelationFactoryBase;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory extends RelationFactoryBase<VkID, VkUser, VkRelationType, VkRelation> {
  public VkRelationFactory(VkUserFactory userFactory) {
    super(userFactory);
  }

  @Override
  public VkRelation create(VkUser user, VkRelationType type) {
    return new VkRelationImpl(user, type);
  }

  @Data
  private static class VkRelationImpl implements VkRelation {
    private final VkUser user;
    private final VkRelationType type;
  }
}
