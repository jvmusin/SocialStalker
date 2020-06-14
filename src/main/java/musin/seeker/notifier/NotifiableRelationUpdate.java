package musin.seeker.notifier;

import musin.seeker.relation.RelationUpdate;
import musin.seeker.relation.User;

import java.time.LocalDateTime;

public interface NotifiableRelationUpdate<TUser extends User, TRelationType> extends RelationUpdate<TUser, TRelationType> {
  Integer getId();

  TUser getOwner();

  LocalDateTime getTime();
}
