package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface UpdaterFactory {
  Updater create(Stalker stalker);
}
