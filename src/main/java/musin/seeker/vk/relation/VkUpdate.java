package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.db.model.RelationChange;
import musin.seeker.relation.Update;

import java.time.LocalDateTime;

@Data
public class VkUpdate implements Update<VkUser, VkRelation> {
  private final VkUser target;
  private final VkRelation was;
  private final VkRelation now;

  public RelationChange toRelationChange(int owner) {
    return RelationChange.builder()
        .owner(owner)
        .target(target.getId())
        .prevType(was.getType())
        .curType(now.getType())
        .time(LocalDateTime.now())
        .build();
  }
}
