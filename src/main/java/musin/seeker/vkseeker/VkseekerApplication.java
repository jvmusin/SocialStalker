package musin.seeker.vkseeker;

import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import musin.seeker.vkseeker.telegram.TelegramChangesNotifier;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.TimeZone;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledFuture;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class VkseekerApplication {

  public static void main(String[] args) {
    SpringApplication.run(VkseekerApplication.class, args);
  }

  @Bean
  @Profile("default")
  public RestTemplate defaultRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  @Profile("!dev")
  public ScheduledFuture<?> scheduleUpdates(TaskScheduler taskScheduler, ScheduledSeeker scheduledSeeker) {
    return taskScheduler.scheduleWithFixedDelay(scheduledSeeker::run, Duration.ofMinutes(5));
  }

  @Bean
  @Profile("dev")
  public ScheduledFuture<?> scheduleUpdatesDev(TaskScheduler taskScheduler, ScheduledSeeker scheduledSeeker) {
    return taskScheduler.scheduleWithFixedDelay(scheduledSeeker::run, Duration.ofSeconds(5));
  }

  @Bean
  @Profile("!tgProxy")
  public ChangesNotifier telegramChangesNotifier(VkApi vkApi) {
    return new TelegramChangesNotifier(vkApi, new RestTemplate());
  }

  @Bean
  @Profile("tgProxy")
  public ChangesNotifier telegramChangesNotifierWithProxy(VkApi vkApi) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

    Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("95.110.194.245", 26518));
    factory.setProxy(proxy);

    return new TelegramChangesNotifier(vkApi, new RestTemplate(factory));
  }

  @PostConstruct
  public void init() {
    LogManager.getLogger(VkseekerApplication.class).info("FJP parallelism is " + ForkJoinPool.getCommonPoolParallelism());
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Samara"));
  }
}