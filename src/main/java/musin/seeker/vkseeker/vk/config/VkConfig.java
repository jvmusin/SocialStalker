package musin.seeker.vkseeker.vk.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("vk")
@Configuration
public class VkConfig {

  @Bean
  public VkApiClient vkApiClient(TransportClient transportClient) {
    return new VkApiClient(transportClient);
  }

  @Bean
  public UserActor defaultUserActor(VkConfigurationProperties config) {
    return new UserActor(config.getUserId(), config.getUserAccessToken());
  }
}
