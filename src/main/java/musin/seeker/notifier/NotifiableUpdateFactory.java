package musin.seeker.notifier;

import musin.seeker.db.update.RelationUpdate;

public interface NotifiableUpdateFactory<
    TUser,
    TRelationType,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>> {
  TNotifiableUpdate create(RelationUpdate relationUpdate);
}
