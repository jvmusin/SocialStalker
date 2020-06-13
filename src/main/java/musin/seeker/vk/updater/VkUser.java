package musin.seeker.vk.updater;

import musin.seeker.notifier.User;
import org.jetbrains.annotations.NotNull;

public interface VkUser extends User, Comparable<VkUser> {
  int getId();

  @Override
  default int compareTo(@NotNull VkUser o) {
    return Integer.compare(getId(), o.getId());
  }
}
