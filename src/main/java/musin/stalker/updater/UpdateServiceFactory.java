package musin.stalker.updater;

import musin.stalker.db.model.Stalker;

public interface UpdateServiceFactory {
  UpdateService create(Stalker stalker);
}
