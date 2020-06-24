package musin.socialstalker.vk.config;

import lombok.Data;
import musin.socialstalker.config.UpdaterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "vk")
public class VkConfigurationProperties implements UpdaterConfig {
  private Duration minDelayBetweenRequests;
  private int requestsPerSecond;
  private int userId;
  private String userAccessToken;
  private Duration periodBetweenUpdates;
}
