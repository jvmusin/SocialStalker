package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.LocalDateTime.now;

@Component
public class MyHttpTransportClient extends HttpTransportClient {

    private LocalDateTime lastTimeUsed = now();
    private Duration minDelay = Duration.ofMillis(400);
    private ReentrantLock lock = new ReentrantLock(true);
    
    public MyHttpTransportClient() {
    }

    public MyHttpTransportClient(int retryAttemptsNetworkErrorCount, int retryAttemptsInvalidStatusCount) {
        super(retryAttemptsNetworkErrorCount, retryAttemptsInvalidStatusCount);
    }

    @SneakyThrows
    private <T> T execute(Callable<T> work) {
        try {
            lock.lock();
            Duration d = Duration.between(lastTimeUsed, now());
            long sleepMillis = minDelay.minus(d).toMillis();
            if (sleepMillis > 0) Thread.sleep(sleepMillis);
            return work.call();
        } finally {
            lastTimeUsed = now();
            lock.unlock();
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