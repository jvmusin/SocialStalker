package musin.seeker.vk.config;

import lombok.Data;
import musin.seeker.updater.ScheduledUpdaterRule;

import java.time.Duration;

@Data
public class VkScheduledUpdaterRule implements ScheduledUpdaterRule {
  private final Runnable updater;
  private final Duration period;
}