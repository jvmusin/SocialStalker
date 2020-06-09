package musin.seeker.vkseeker.config;

import musin.seeker.vkseeker.core.RelationsUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;

@Configuration
public class RelationsUpdaterConfig {

  @Bean
  public ScheduledFuture<?> scheduleUpdates(TaskScheduler taskScheduler, RelationsUpdater relationsUpdater, RelationsUpdaterConfigurationProperties config) {
    return taskScheduler.scheduleWithFixedDelay(relationsUpdater, config.getPeriod());
  }
}
