package musin.stalker.instagram.config;

import musin.stalker.config.NetworkNames;
import musin.stalker.config.NetworkProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramNetworkProperties implements NetworkProperties {
  @Override
  public String getNetwork() {
    return NetworkNames.INSTAGRAM;
  }
}