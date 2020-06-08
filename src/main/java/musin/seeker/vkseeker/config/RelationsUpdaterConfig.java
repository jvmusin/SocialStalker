package musin.seeker.vkseeker.config;

import musin.seeker.vkseeker.core.RelationsUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class RelationsUpdaterConfig {

  @Bean
  @Profile("!dev")
  public ScheduledFuture<?> scheduleUpdates(TaskScheduler taskScheduler, RelationsUpdater relationsUpdater) {
    return taskScheduler.scheduleWithFixedDelay(relationsUpdater::run, Duration.ofMinutes(5));
  }

  @Bean
  @Profile("dev")
  public ScheduledFuture<?> scheduleUpdatesDev(TaskScheduler taskScheduler, RelationsUpdater relationsUpdater) {
    return taskScheduler.scheduleWithFixedDelay(relationsUpdater::run, Duration.ofSeconds(30));
  }
}
