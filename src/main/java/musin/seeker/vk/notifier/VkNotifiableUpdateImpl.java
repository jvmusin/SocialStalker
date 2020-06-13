package musin.seeker.vk.notifier;

import lombok.Data;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkUser;

import java.time.LocalDateTime;

@Data
public class VkNotifiableUpdateImpl implements VkNotifiableUpdate {
  private final Integer id;
  private final VkUser owner;
  private final VkUser target;
  private final VkRelation was;
  private final VkRelation now;
  private final LocalDateTime time;
}
