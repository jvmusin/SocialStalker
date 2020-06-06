package musin.seeker.vkseeker.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import musin.seeker.vkseeker.api.MusinUserActor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfig {

  @Bean
  public VkApiClient vkApiClient(TransportClient transportClient) {
    return new VkApiClient(transportClient);
  }

  @Bean
  public UserActor defaultUserActor() {
    return new MusinUserActor();
  }
}
