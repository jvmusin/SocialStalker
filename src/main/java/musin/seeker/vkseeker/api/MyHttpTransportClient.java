package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

@Component
public class MyHttpTransportClient extends HttpTransportClient {

  private static final int DELAY_BETWEEN_REQUESTS_MILLIS = 1000;
  private static final int REQUESTS_PER_SECOND = 3;

  private final Semaphore availableRequestsSemaphore = new Semaphore(REQUESTS_PER_SECOND, true);
  private final ScheduledExecutorService executor = newScheduledThreadPool(REQUESTS_PER_SECOND);

  public MyHttpTransportClient() {
  }

  public MyHttpTransportClient(int retryAttemptsNetworkErrorCount, int retryAttemptsInvalidStatusCount) {
    super(retryAttemptsNetworkErrorCount, retryAttemptsInvalidStatusCount);
  }

  @SneakyThrows
  private <T> T execute(Callable<T> work) {
    try {
      availableRequestsSemaphore.acquireUninterruptibly();
      return work.call();
    } finally {
      executor.schedule((Runnable) availableRequestsSemaphore::release, DELAY_BETWEEN_REQUESTS_MILLIS, TimeUnit.MILLISECONDS);
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
