package musin.seeker.vkseeker.config;

import musin.seeker.vkseeker.ScheduledSeeker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class ScheduledSeekerConfig {

  @Bean
  @Profile("!dev")
  public ScheduledFuture<?> scheduleUpdates(TaskScheduler taskScheduler, ScheduledSeeker scheduledSeeker) {
    return taskScheduler.scheduleWithFixedDelay(scheduledSeeker::run, Duration.ofMinutes(1));
  }

  @Bean
  @Profile("dev")
  public ScheduledFuture<?> scheduleUpdatesDev(TaskScheduler taskScheduler, ScheduledSeeker scheduledSeeker) {
    return taskScheduler.scheduleWithFixedDelay(scheduledSeeker::run, Duration.ofSeconds(10));
  }
}
