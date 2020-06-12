package musin.seeker.updater;

import java.time.Duration;

public interface ScheduledUpdaterRule {
  Runnable getUpdater();

  Duration getPeriod();
}
