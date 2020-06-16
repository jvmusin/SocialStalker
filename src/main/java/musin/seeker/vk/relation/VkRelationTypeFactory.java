package musin.seeker.vk.relation;

import musin.seeker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class VkRelationTypeFactory implements RelationTypeFactory<VkRelationType> {
  @Override
  public VkRelationType parseNullSafe(String type) {
    return type == null ? null : VkRelationType.valueOf(type);
  }
}
