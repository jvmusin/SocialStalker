package musin.stalker.updater;

import musin.stalker.db.model.Stalker;

import java.time.Duration;

public interface UpdaterFactory {
  Updater create(Stalker stalker);

  Duration getPeriodBetweenUpdates();
}
