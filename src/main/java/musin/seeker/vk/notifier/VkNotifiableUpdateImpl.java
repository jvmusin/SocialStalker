package musin.seeker.vk.notifier;

import lombok.Data;
import musin.seeker.vk.db.VkRelationType;
import musin.seeker.vk.relation.VkUser;

import java.time.LocalDateTime;

@Data
public class VkNotifiableUpdateImpl implements VkNotifiableUpdate {
  private final Integer id;
  private final VkUser owner;
  private final VkUser target;
  private final VkRelationType was;
  private final VkRelationType now;
  private final LocalDateTime time;
}
