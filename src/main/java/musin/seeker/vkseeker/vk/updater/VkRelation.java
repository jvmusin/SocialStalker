package musin.seeker.vkseeker.vk.updater;

import lombok.Data;
import musin.seeker.vkseeker.db.model.RelationType;
import musin.seeker.vkseeker.notifier.User;
import musin.seeker.vkseeker.relation.Relation;

@Data
public class VkRelation implements Relation<User, RelationType> {
  private final User user;
  private final RelationType type;
}
