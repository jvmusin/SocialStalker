package musin.seeker.vkseeker.config;

import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import musin.seeker.vkseeker.telegram.TelegramChangesNotifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@Profile("tg")
public class TelegramConfig {

  @Bean("tgRestTemplate")
  @Profile("!tgProxy")
  public RestTemplate tgRestTemplate() {
    return new RestTemplate();
  }

  @Bean("tgRestTemplate")
  @Profile("tgProxy")
  public RestTemplate tgRestTemplateProxy() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

    Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("95.110.194.245", 26518));
    factory.setProxy(proxy);

    return new RestTemplate(factory);
  }

  @Bean
  public ChangesNotifier telegramChangesNotifier(VkApi vkApi, @Qualifier("tgRestTemplate") RestTemplate restTemplate) {
    return new TelegramChangesNotifier(vkApi, restTemplate);
  }
}
