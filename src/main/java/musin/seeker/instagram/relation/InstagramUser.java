package musin.seeker.instagram.relation;

import musin.seeker.notifier.User;
import org.jetbrains.annotations.NotNull;

public interface InstagramUser extends User, Comparable<InstagramUser> {
  long getId();

  @Override
  default int compareTo(@NotNull InstagramUser o) {
    return Long.compare(getId(), o.getId());
  }
}
