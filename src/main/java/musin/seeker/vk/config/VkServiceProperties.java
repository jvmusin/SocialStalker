package musin.seeker.vk.config;

import musin.seeker.config.ServiceNames;
import musin.seeker.config.ServiceProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkServiceProperties implements ServiceProperties {
  @Override
  public String getResource() {
    return ServiceNames.VK;
  }
}
