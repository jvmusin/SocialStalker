package musin.seeker.vkseeker.vk.relation;

import lombok.Data;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.RelationType;
import musin.seeker.vkseeker.relation.Relation;

@Data
public class VkRelation implements Relation<Integer, RelationType> {
  private final Integer user;
  private final RelationType type;

  public static VkRelation fromDb(RelationChange relationChange) {
    return new VkRelation(relationChange.getTarget(), relationChange.getCurType());
  }
}
