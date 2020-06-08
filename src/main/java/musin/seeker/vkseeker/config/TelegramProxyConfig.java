package musin.seeker.vkseeker.config;

import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.notifiers.TelegramChangesNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@Profile("tg")
public class TelegramProxyConfig {

  @Bean("tgRestTemplate")
  @Profile("!tgProxy")
  public RestTemplate tgRestTemplate() {
    return new RestTemplate();
  }

  @Bean("tgRestTemplate")
  @Profile("tgProxy")
  public RestTemplate tgRestTemplateProxy(TelegramProxyConfigurationProperties config) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

    Proxy proxy = new Proxy(config.getType(), new InetSocketAddress(config.getHostname(), config.getPort()));
    factory.setProxy(proxy);

    return new RestTemplate(factory);
  }

  @Bean
  public TelegramChangesNotifier telegramChangesNotifier(VkApi vkApi, RestTemplate restTemplate, AsyncListenableTaskExecutor taskExecutor) {
    return new TelegramChangesNotifier(vkApi, restTemplate, taskExecutor);
  }
}
