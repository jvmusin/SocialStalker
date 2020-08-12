package musin.stalker.notifier;

import musin.stalker.db.model.RelationUpdate;

public interface NotifiableUpdateFactory {
  NotifiableUpdate create(RelationUpdate relationUpdate);
}
