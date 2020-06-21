package musin.seeker.instagram.config;

import musin.seeker.config.NetworkNames;
import musin.seeker.config.NetworkProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramNetworkProperties implements NetworkProperties {
  @Override
  public String getNetwork() {
    return NetworkNames.INSTAGRAM;
  }
}
