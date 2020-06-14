package musin.seeker.instagram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "instagram")
public class InstagramConfigurationProperties {
  private String username;
  private String password;
  private Duration period;
}
