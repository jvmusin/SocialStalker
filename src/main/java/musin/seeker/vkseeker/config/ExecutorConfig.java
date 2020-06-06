package musin.seeker.vkseeker.config;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class ExecutorConfig {

  @Bean
  public TaskExecutorCustomizer taskExecutorCustomizer() {
    return executor -> {
      executor.setQueueCapacity(0);
      executor.setCorePoolSize(0);
      executor.setKeepAliveSeconds(60 * 2);
      executor.setThreadNamePrefix("my-task-executor");
    };
  }

  @Bean
  public TaskSchedulerCustomizer taskSchedulerCustomizer() {
    //todo play with it
    return scheduler -> {
      scheduler.setPoolSize(10);
      scheduler.setThreadNamePrefix("my-task-scheduler");
    };
  }
}
