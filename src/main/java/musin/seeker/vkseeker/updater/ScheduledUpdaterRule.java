package musin.seeker.vkseeker.updater;

import java.time.Duration;

public interface ScheduledUpdaterRule {
  Runnable getUpdater();

  Duration getPeriod();
}
