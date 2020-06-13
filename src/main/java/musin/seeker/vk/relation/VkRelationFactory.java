package musin.seeker.vk.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkRelationFactory {

  private final VkUserFactory vkUserFactory;

  public VkRelation create(int userId, RelationType relationType) {
    return new VkRelationImpl(vkUserFactory.create(userId), relationType);
  }

  public VkRelation create(VkUser user, RelationType relationType) {
    return new VkRelationImpl(user, relationType);
  }

  @Data
  private static class VkRelationImpl implements VkRelation {
    private final VkUser user;
    private final RelationType type;
  }
}
