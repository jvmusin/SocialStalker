package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface UpdateServiceFactory<ID> {
  UpdateService<ID> create(Stalker stalker);
}
