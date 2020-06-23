package musin.socialstalker.vk.relation;

import lombok.Data;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateFactory implements UpdateFactory<VkRelationType> {
  @Override
  public VkUpdate updating(User<?> user, VkRelationType was, VkRelationType now) {
    return new VkUpdateImpl(user, was, now);
  }

  @Data
  private static class VkUpdateImpl implements VkUpdate {
    private final User<?> suspected;
    private final VkRelationType was;
    private final VkRelationType now;
  }
}
