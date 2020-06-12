package musin.seeker.vkseeker.relation;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.vk.updater.VkUpdater;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
//todo rewrite it and remove explicit vk parts
public class RelationsUpdaterScheduler {
  private final VkUpdater updater;
  private final TaskScheduler taskScheduler;
  private final RelationsUpdaterConfigurationProperties config;

  @EventListener(ApplicationReadyEvent.class)
  public void scheduleUpdates() {
    taskScheduler.scheduleWithFixedDelay(updater, config.getPeriod());
  }
}
