package musin.seeker.vkseeker.vk.updater;

import lombok.Data;
import musin.seeker.vkseeker.updater.ScheduledUpdaterRule;

import java.time.Duration;

@Data
public class VkScheduledUpdaterRule implements ScheduledUpdaterRule {
  private final Runnable updater;
  private final Duration period;
}
