package musin.seeker.relation;

import musin.seeker.notifier.User;

public interface Update<TUser extends User, TRelationType> {
  TUser getTarget();

  TRelationType getWas();

  TRelationType getNow();
}
