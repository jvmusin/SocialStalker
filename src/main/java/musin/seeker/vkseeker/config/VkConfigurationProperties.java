package musin.seeker.vkseeker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "vk")
public class VkConfigurationProperties {
  private Duration delayBetweenRequests;
  private int requestsPerSecond;
  private int userId;
  private String userAccessToken;
}
