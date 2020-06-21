package musin.socialstalker.updater;

import java.time.Duration;

public interface Updater extends Runnable {
  Duration getPeriodBetweenUpdates();
}
