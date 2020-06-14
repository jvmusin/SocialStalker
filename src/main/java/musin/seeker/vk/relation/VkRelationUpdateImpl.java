package musin.seeker.vk.relation;

import lombok.Data;

@Data
public class VkRelationUpdateImpl implements VkRelationUpdate {
  private final VkUser target;
  private final VkRelationType was;
  private final VkRelationType now;
}
