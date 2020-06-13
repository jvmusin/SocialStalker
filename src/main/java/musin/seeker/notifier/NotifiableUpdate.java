package musin.seeker.notifier;

import musin.seeker.relation.Update;

import java.time.LocalDateTime;

public interface NotifiableUpdate<TRelation> extends Update<TRelation> {
  int getId();

  User getOwner();

  LocalDateTime getTime();
}
