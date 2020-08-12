package musin.stalker.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfigurationProperties {
  private String botToken;
  private long adminUid;
}
