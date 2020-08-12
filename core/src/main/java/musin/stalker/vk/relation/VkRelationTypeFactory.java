package musin.stalker.vk.relation;

import musin.stalker.relation.RelationType;
import musin.stalker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class VkRelationTypeFactory implements RelationTypeFactory {
  @Override
  public RelationType parseNullSafe(String type) {
    return type == null ? null : VkRelationType.valueOf(type);
  }
}
