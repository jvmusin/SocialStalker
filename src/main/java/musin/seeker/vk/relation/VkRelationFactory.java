package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.relation.RelationFactory;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory implements RelationFactory<VkUser, VkRelationType, VkRelation> {
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
