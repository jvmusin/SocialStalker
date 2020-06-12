package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledUpdater {
  private final List<ScheduledUpdaterRule> rules;
  private final TaskScheduler taskScheduler;

  @EventListener(ApplicationReadyEvent.class)
  public void scheduleUpdates() {
    rules.forEach(rule -> taskScheduler.scheduleWithFixedDelay(rule.getUpdater(), rule.getPeriod()));
  }
}
