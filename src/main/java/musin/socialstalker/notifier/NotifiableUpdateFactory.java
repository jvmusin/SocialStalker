package musin.socialstalker.notifier;

import musin.socialstalker.db.model.RelationUpdate;

public interface NotifiableUpdateFactory {
  NotifiableUpdate create(RelationUpdate relationUpdate);
}
