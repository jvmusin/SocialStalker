package musin.seeker.vk.relation;

import lombok.Data;
import musin.seeker.vk.db.VkRelationType;

@Data
public class VkUpdateImpl implements VkUpdate {
  private final VkUser target;
  private final VkRelationType was;
  private final VkRelationType now;
}
