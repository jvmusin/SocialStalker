package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.db.model.RelationChange;
import musin.seeker.db.model.RelationType;
import musin.seeker.relation.Relation;

@Data
public class VkRelation implements Relation<Integer, RelationType> {
  private final Integer user;
  private final RelationType type;

  public static VkRelation fromDb(RelationChange relationChange) {
    return new VkRelation(relationChange.getTarget(), relationChange.getCurType());
  }
}
