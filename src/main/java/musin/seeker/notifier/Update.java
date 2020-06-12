package musin.seeker.notifier;

import musin.seeker.relation.Relation;

import java.time.LocalDateTime;

public interface Update<TRelation extends Relation<User, ?>> {
  int getId();

  User getOwner();

  User getTarget();

  TRelation getWas();

  TRelation getNow();

  LocalDateTime getTime();
}
