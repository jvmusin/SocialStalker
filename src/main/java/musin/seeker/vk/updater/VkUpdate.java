package musin.seeker.vk.updater;

import lombok.Data;
import musin.seeker.db.model.RelationChange;
import musin.seeker.notifier.User;
import musin.seeker.relation.Update;
import musin.seeker.vk.relation.VkRelation;

import java.time.LocalDateTime;

@Data
public class VkUpdate implements Update<VkRelation> {
  private final User target;
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
