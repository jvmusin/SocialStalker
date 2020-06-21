package musin.socialstalker.vk.relation;

import lombok.Data;
import musin.socialstalker.relation.UpdateFactory;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateFactory implements UpdateFactory<VkUser, VkRelationType, VkUpdate> {
  @Override
  public VkUpdate updating(VkUser user, VkRelationType was, VkRelationType now) {
    return new VkUpdateImpl(user, was, now);
  }

  @Data
  private static class VkUpdateImpl implements VkUpdate {
    private final VkUser suspected;
    private final VkRelationType was;
    private final VkRelationType now;
  }
}
