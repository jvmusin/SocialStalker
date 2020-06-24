package musin.socialstalker.vk.relation;

import lombok.Data;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateFactory implements UpdateFactory {
  @Override
  public VkUpdate updating(User user, RelationType was, RelationType now) {
    return new VkUpdateImpl(user, was, now);
  }

  @Data
  private static class VkUpdateImpl implements VkUpdate {
    private final User suspected;
    private final RelationType was;
    private final RelationType now;
  }
}
