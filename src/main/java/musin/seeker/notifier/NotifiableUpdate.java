package musin.seeker.notifier;

import musin.seeker.relation.Update;

import java.time.LocalDateTime;

public interface NotifiableUpdate<TUser extends User, TRelation> extends Update<TUser, TRelation> {
  Integer getId();

  TUser getOwner();

  LocalDateTime getTime();
}
