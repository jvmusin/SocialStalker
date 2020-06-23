package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.relation.RelationType;

public interface UpdateServiceFactory<ID, TRelationType extends RelationType> {
  UpdateService<ID, RelationType> create(Stalker stalker);
}
