package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.relation.Relation;

@Data
public class VkRelation implements Relation<VkUser, VkRelationType> {
  private final VkUser user;
  private final VkRelationType type;
}
