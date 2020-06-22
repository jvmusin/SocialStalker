package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

import java.time.Duration;

public interface UpdaterFactory {
  Updater create(Stalker stalker);

  Duration getPeriodBetweenUpdates();
}
