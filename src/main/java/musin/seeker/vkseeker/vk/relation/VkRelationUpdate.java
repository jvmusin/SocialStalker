package musin.seeker.vkseeker.vk.relation;

import lombok.Data;
import musin.seeker.vkseeker.db.model.RelationChange;

import java.time.LocalDateTime;

@Data
public class VkRelationUpdate {
  private final Integer user;
  private final VkRelation was;
  private final VkRelation now;

  public RelationChange toDb(int owner) {
    return RelationChange.builder()
        .owner(owner)
        .target(user)
        .prevType(was.getType())
        .curType(now.getType())
        .time(LocalDateTime.now())
        .build();
  }
}
