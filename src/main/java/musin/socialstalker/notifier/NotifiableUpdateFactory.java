package musin.socialstalker.notifier;

import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.relation.User;

public interface NotifiableUpdateFactory<
    TRelationType,
    TNotifiableUpdate extends NotifiableUpdate<TRelationType>> {
  TNotifiableUpdate create(RelationUpdate relationUpdate);
}
