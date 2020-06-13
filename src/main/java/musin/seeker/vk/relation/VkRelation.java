package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.db.model.RelationType;
import musin.seeker.relation.Relation;

@Data
public class VkRelation implements Relation<VkUser, RelationType> {
  private final VkUser user;
  private final RelationType type;
}
