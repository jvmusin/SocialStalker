package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.relation.Relation;
import musin.seeker.vk.db.VkRelationType;

@Data
public class VkRelation implements Relation<VkUser, VkRelationType> {
  private final VkUser user;
  private final VkRelationType type;
}
