package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.db.repository.StalkerRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdaterScheduler {
  private final StalkerRepository stalkerRepository;
  private final List<UpdaterFactory> factories;
  private final TaskScheduler taskScheduler;

  @EventListener(ApplicationReadyEvent.class)
  public void scheduleUpdates() {
    factories.forEach(
        factory -> taskScheduler.scheduleWithFixedDelay(
            () -> stalkerRepository.findAll().stream()
                .map(factory::create)
                .forEach(Runnable::run),
            factory.getPeriodBetweenUpdates()
        )
    );
  }
}
