package musin.socialstalker.vk.relation;

import musin.socialstalker.relation.*;
import org.springframework.stereotype.Component;

@Component
public class VkRelationFactory implements RelationFactory {
  @Override
  public Relation create(User user, RelationType type) {
    return new RelationImpl(user, type);
  }
}
