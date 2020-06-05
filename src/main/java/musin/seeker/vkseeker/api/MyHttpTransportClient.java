package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

@Component
@AllArgsConstructor
public class MyHttpTransportClient extends HttpTransportClient {

  private static final int DELAY_BETWEEN_REQUESTS_MILLIS = 1000;
  private static final int REQUESTS_PER_SECOND = 3;

  private final Semaphore availableRequestsSemaphore = new Semaphore(REQUESTS_PER_SECOND, true);
  private final TaskScheduler taskScheduler;

  @SneakyThrows
  private <T> T execute(Callable<T> work) {
    try {
      availableRequestsSemaphore.acquireUninterruptibly();
      return work.call();
    } finally {
      taskScheduler.schedule(availableRequestsSemaphore::release, Instant.now().plusMillis(DELAY_BETWEEN_REQUESTS_MILLIS));
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
