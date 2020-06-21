package musin.socialstalker.vk.config;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import musin.socialstalker.util.TimedSemaphore;
import musin.socialstalker.util.TimedSemaphoreFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Callable;

@Component
public class VkHttpTransportClient extends HttpTransportClient {

  private final TimedSemaphore semaphore;

  public VkHttpTransportClient(TimedSemaphoreFactory timedSemaphoreFactory, VkConfigurationProperties config) {
    semaphore = timedSemaphoreFactory.create(config.getRequestsPerSecond(), config.getMinDelayBetweenRequests());
  }

  @SneakyThrows
  private <T> T execute(Callable<T> task) {
    return semaphore.execute(task);
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
