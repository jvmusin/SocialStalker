package musin.seeker.relation;

import musin.seeker.notifier.User;

public interface Update<TRelation> {
  User getTarget();

  TRelation getWas();

  TRelation getNow();
}
