package musin.seeker.vk.relation;

import lombok.Data;

@Data
public class VkUpdateImpl implements VkUpdate {
  private final VkUser target;
  private final VkRelation was;
  private final VkRelation now;
}
