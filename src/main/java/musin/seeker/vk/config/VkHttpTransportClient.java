package musin.seeker.vk.config;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

@Component
public class VkHttpTransportClient extends HttpTransportClient {

  private final TaskScheduler taskScheduler;
  private final long delayBetweenRequestsMillis;
  private final Semaphore availableRequestsSemaphore;

  public VkHttpTransportClient(TaskScheduler taskScheduler, VkConfigurationProperties config) {
    this.taskScheduler = taskScheduler;
    this.delayBetweenRequestsMillis = config.getMinDelayBetweenRequests().toMillis();
    this.availableRequestsSemaphore = new Semaphore(config.getRequestsPerSecond(), true);
  }

  @SneakyThrows
  private <T> T execute(Callable<T> work) {
    try {
      availableRequestsSemaphore.acquireUninterruptibly();
      return work.call();
    } finally {
      taskScheduler.schedule(availableRequestsSemaphore::release, Instant.now().plusMillis(delayBetweenRequestsMillis));
    }
  }

  @Override
  public ClientResponse get(String url, String contentType) {
    return execute(() -> super.get(url, contentType));
  }

  @Override
  public ClientResponse post(String url, String fileName, File file) {
    return execute(() -> super.post(url, fileName, file));
  }

  @Override
  public ClientResponse post(String url, String body, String contentType) {
    return execute(() -> super.post(url, body, contentType));
  }

  @Override
  public ClientResponse delete(String url, String body, String contentType) {
    return execute(() -> super.delete(url, body, contentType));
  }
}
