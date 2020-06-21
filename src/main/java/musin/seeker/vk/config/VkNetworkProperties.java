package musin.seeker.vk.config;

import musin.seeker.config.NetworkNames;
import musin.seeker.config.NetworkProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkNetworkProperties implements NetworkProperties {
  @Override
  public String getNetwork() {
    return NetworkNames.VK;
  }
}
