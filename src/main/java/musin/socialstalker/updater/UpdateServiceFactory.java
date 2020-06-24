package musin.socialstalker.updater;

import musin.socialstalker.api.Id;
import musin.socialstalker.db.model.Stalker;

public interface UpdateServiceFactory<ID extends Id> {
  UpdateService<ID> create(Stalker stalker);
}
