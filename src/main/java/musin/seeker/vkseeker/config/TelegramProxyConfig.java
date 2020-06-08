package musin.seeker.vkseeker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
}
