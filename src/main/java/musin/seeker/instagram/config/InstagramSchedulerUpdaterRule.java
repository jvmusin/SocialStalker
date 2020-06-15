package musin.seeker.instagram.config;

import lombok.Data;
import musin.seeker.updater.ScheduledUpdaterRule;

import java.time.Duration;

@Data
public class InstagramSchedulerUpdaterRule implements ScheduledUpdaterRule {
  private final Runnable updater;
  private final Duration period;
}
