package musin.seeker.relation;

import musin.seeker.notifier.User;

public interface Update<TUser extends User, TRelation> {
  TUser getTarget();

  TRelation getWas();

  TRelation getNow();
}
