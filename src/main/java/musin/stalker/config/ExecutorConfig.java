package musin.stalker.config;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@Configuration
@EnableScheduling
public class ExecutorConfig {

  @Bean
  public TaskExecutorCustomizer taskExecutorCustomizer() {
    return executor -> {
      executor.setQueueCapacity(0);
      executor.setCorePoolSize(0);
      executor.setThreadNamePrefix("my-task-executor");
    };
  }

  @Bean
  public TaskSchedulerCustomizer taskSchedulerCustomizer() {
    return scheduler -> {
      scheduler.setPoolSize(10);
      scheduler.setThreadNamePrefix("my-task-scheduler");
    };
  }
}
