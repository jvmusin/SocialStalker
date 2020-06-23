package musin.socialstalker.vk.relation;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class VkRelationTypeFactory implements RelationTypeFactory<RelationType> {
  @Override
  public VkRelationType parseNullSafe(String type) {
    return type == null ? null : VkRelationType.valueOf(type);
  }
}
