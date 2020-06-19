package musin.seeker.notifier;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.relation.User;

public interface NotifiableUpdateFactory<
    TUser extends User<?>,
    TRelationType,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>> {
  TNotifiableUpdate create(RelationUpdate relationUpdate);
}
