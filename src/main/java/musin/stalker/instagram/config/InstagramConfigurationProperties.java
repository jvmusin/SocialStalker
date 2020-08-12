package musin.stalker.instagram.config;

import lombok.Data;
import musin.stalker.config.UpdaterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "instagram")
public class InstagramConfigurationProperties implements UpdaterConfig {
  private String username;
  private String password;
  private Duration periodBetweenUpdates;
}
