package musin.seeker.updater;

import musin.seeker.db.model.Stalker;

public interface UpdaterFactory {
  Updater create(Stalker stalker);
}
