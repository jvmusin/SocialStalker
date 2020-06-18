package musin.seeker.notifier;

import musin.seeker.relation.Update;

import java.time.LocalDateTime;

public interface NotifiableUpdate<TUser, TRelationType> extends Update<TUser, TRelationType> {
  String getResource();

  Integer getId();

  TUser getOwner();

  LocalDateTime getTime();
}
