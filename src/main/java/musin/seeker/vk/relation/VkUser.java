package musin.seeker.vk.relation;

import musin.seeker.relation.User;
import org.jetbrains.annotations.NotNull;

public interface VkUser extends User, Comparable<VkUser> {
  Integer getId();

  @Override
  default int compareTo(@NotNull VkUser o) {
    return Integer.compare(getId(), o.getId());
  }
}
