package musin.seeker.instagram.config;

import musin.seeker.config.ServiceProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramServiceProperties implements ServiceProperties {
  @Override
  public String getResource() {
    return "INSTAGRAM";
  }
}
