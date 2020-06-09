package musin.seeker.vkseeker.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "executors")
@AllArgsConstructor
public class ThreadPoolWatcherActuatorEndpoint {
  private final ThreadPoolTaskExecutor taskExecutor;
  private final ThreadPoolTaskScheduler taskScheduler;

  @ReadOperation
  public String read() {
    return taskExecutor.getThreadPoolExecutor() + "\n" + taskScheduler.getScheduledThreadPoolExecutor();
  }
}