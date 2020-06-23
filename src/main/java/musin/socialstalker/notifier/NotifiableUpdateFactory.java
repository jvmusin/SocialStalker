package musin.socialstalker.notifier;

import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.relation.RelationType;

public interface NotifiableUpdateFactory<TRelationType extends RelationType> {
  NotifiableUpdate<RelationType> create(RelationUpdate relationUpdate);
}
