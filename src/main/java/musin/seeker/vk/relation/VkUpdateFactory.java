package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.relation.UpdateFactory;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateFactory implements UpdateFactory<VkUser, VkRelationType, VkUpdate> {
  @Override
  public VkUpdate create(VkUser user, VkRelationType was, VkRelationType now) {
    return new VkUpdateImpl(user, was, now);
  }

  @Data
  private static class VkUpdateImpl implements VkUpdate {
    private final VkUser target;
    private final VkRelationType was;
    private final VkRelationType now;
  }
}
