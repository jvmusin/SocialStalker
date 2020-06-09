package musin.seeker.vkseeker.telegram;

import lombok.Data;
import musin.seeker.vkseeker.config.ProxyConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfigurationProperties {
  private boolean enabled;
  @NestedConfigurationProperty
  private ProxyConfigurationProperties proxy;
}
