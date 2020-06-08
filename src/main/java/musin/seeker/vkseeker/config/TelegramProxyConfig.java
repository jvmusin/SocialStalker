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

  public RestTemplate createRestTemplate(TelegramProxyConfigurationProperties proxyConfig) {
    if (!proxyConfig.isEnabled()) return new RestTemplate();

    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

    Proxy proxy = new Proxy(proxyConfig.getType(), new InetSocketAddress(proxyConfig.getHostname(), proxyConfig.getPort()));
    factory.setProxy(proxy);

    return new RestTemplate(factory);
  }

  @Bean
  public TelegramChangesNotifier telegramChangesNotifier(VkApi vkApi, AsyncListenableTaskExecutor taskExecutor, TelegramProxyConfigurationProperties proxyConfig) {
    return new TelegramChangesNotifier(vkApi, createRestTemplate(proxyConfig), taskExecutor);
  }
}
