package musin.socialstalker.notifier;

import musin.socialstalker.db.model.RelationUpdate;

public interface NotifiableUpdateFactory<TRelationType> {
  NotifiableUpdate<TRelationType> create(RelationUpdate relationUpdate);
}
