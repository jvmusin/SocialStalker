package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface UpdateServiceFactory<ID, TRelationType> {
  UpdateService<ID, TRelationType> create(Stalker stalker);
}
