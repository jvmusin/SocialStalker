package musin.seeker.vkseeker.notifier;

import musin.seeker.vkseeker.relation.Relation;

import java.time.LocalDateTime;

public interface Update<TRelation extends Relation<User, ?>> {
  int getId();

  User getOwner();

  User getTarget();

  TRelation getWas();

  TRelation getNow();

  LocalDateTime getTime();
}
