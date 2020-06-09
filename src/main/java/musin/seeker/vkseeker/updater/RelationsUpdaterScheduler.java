package musin.seeker.vkseeker.updater;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RelationsUpdaterScheduler {
  private final RelationsUpdater relationsUpdater;
  private final TaskScheduler taskScheduler;
  private final RelationsUpdaterConfigurationProperties config;

  @EventListener(ApplicationReadyEvent.class)
  public void scheduleUpdates() {
    taskScheduler.scheduleWithFixedDelay(relationsUpdater, config.getPeriod());
  }
}
