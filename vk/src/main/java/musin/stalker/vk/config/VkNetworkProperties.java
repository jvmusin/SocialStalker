package musin.stalker.vk.config;

import musin.stalker.config.NetworkNames;
import musin.stalker.config.NetworkProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkNetworkProperties implements NetworkProperties {
  @Override
  public String getNetwork() {
    return NetworkNames.VK;
  }
}
