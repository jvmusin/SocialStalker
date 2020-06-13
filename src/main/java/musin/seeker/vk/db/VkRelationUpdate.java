package musin.seeker.vk.db;

import lombok.Data;
import musin.seeker.vk.relation.VkUser;

import java.time.LocalDateTime;

@Data
public class VkRelationUpdate {
  Integer id;
  VkUser owner;
  VkUser target;
  VkRelationType was;
  VkRelationType now;
  LocalDateTime time;
}
