package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
public class MyHttpTransportClient extends HttpTransportClient {

  private static final int DELAY_BETWEEN_REQUESTS_MILLIS = 1000;
  private static final int REQUESTS_PER_SECOND = 5;
  private final Semaphore availableRequestsSemaphore = new Semaphore(REQUESTS_PER_SECOND, true);

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
      CompletableFuture.delayedExecutor(DELAY_BETWEEN_REQUESTS_MILLIS, TimeUnit.MILLISECONDS)
          .execute(availableRequestsSemaphore::release);
    }
  }

  @Override
  public ClientResponse get(String url) {
    return execute(() -> super.get(url));
  }

  public ClientResponse post(String url, String body) {
    return execute(() -> super.post(url, body));
  }

  public ClientResponse post(String url, String fileName, File file) {
    return execute(() -> super.post(url, fileName, file));
  }

  public ClientResponse post(String url, String body, String contentType) {
    return execute(() -> super.post(url, body, contentType));
  }

  public ClientResponse get(String url, String contentType) {
    return execute(() -> super.get(url, contentType));
  }

  public ClientResponse post(String url) {
    return execute(() -> super.post(url));
  }

  public ClientResponse delete(String url) {
    return execute(() -> super.delete(url));
  }

  public ClientResponse delete(String url, String body) {
    return execute(() -> super.delete(url, body));
  }

  public ClientResponse delete(String url, String body, String contentType) {
    return execute(() -> super.delete(url, body, contentType));
  }
}