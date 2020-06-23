package musin.socialstalker.vk.relation;

import lombok.Data;
import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory implements RelationFactory<RelationType> {
  @Override
  public VkRelation create(User<?> user, RelationType type) {
    return new VkRelationImpl(user, type);
  }

  @Data
  private static class VkRelationImpl implements VkRelation {
    private final User<?> user;
    private final RelationType type;
  }
}
