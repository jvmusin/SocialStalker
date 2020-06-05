package musin.seeker.vkseeker.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@AllArgsConstructor
@Log4j2
public class ExecutorConfig {

  @Bean("MyTaskExecutor")
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setQueueCapacity(0);
    pool.setCorePoolSize(0);
    pool.setKeepAliveSeconds(60 * 2);
    pool.setThreadNamePrefix("my-task-executor");
    return pool;
  }

  @Bean("MyTaskScheduler")
  public TaskScheduler taskScheduler() {
    //todo play with it
    ThreadPoolTaskScheduler pool = new ThreadPoolTaskScheduler();
    pool.setPoolSize(10);
    pool.setThreadNamePrefix("my-task-scheduler");
    return pool;
  }
}
