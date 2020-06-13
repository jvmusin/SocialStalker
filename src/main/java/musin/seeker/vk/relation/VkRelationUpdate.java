package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.updater.VkUser;

import java.time.LocalDateTime;

@Data
public class VkRelationUpdate {
  private final VkUser user;
  private final VkRelation was;
  private final VkRelation now;

  public RelationChange toDb(int owner) {
    return RelationChange.builder()
        .owner(owner)
        .target(user.getId())
        .prevType(was.getType())
        .curType(now.getType())
        .time(LocalDateTime.now())
        .build();
  }
}
