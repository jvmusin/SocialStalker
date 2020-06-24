package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface UpdateServiceFactory {
  UpdateService create(Stalker stalker);
}
