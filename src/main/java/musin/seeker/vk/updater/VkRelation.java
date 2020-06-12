package musin.seeker.vk.updater;

import lombok.Data;
import musin.seeker.notifier.User;
import musin.seeker.db.model.RelationType;
import musin.seeker.relation.Relation;

@Data
public class VkRelation implements Relation<User, RelationType> {
  private final User user;
  private final RelationType type;
}
