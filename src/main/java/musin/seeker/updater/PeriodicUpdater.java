package musin.seeker.updater;

import java.time.Duration;

public interface PeriodicUpdater extends Runnable {
  Duration getPeriodBetweenUpdates();
}
