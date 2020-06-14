package musin.seeker.notifier;

import musin.seeker.relation.Update;
import musin.seeker.relation.User;

import java.time.LocalDateTime;

public interface NotifiableUpdate<TUser extends User, TRelationType> extends Update<TUser, TRelationType> {
  Integer getId();

  TUser getOwner();

  LocalDateTime getTime();
}
