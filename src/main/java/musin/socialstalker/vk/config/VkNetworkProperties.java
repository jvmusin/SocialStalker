package musin.socialstalker.vk.config;

import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.config.NetworkProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkNetworkProperties implements NetworkProperties {
  @Override
  public String getNetwork() {
    return NetworkNames.VK;
  }
}
