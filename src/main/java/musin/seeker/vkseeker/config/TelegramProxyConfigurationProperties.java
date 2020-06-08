package musin.seeker.vkseeker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.Proxy;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram.proxy")
public class TelegramProxyConfigurationProperties {
  private Proxy.Type type;
  private String hostname;
  private int port;
}
