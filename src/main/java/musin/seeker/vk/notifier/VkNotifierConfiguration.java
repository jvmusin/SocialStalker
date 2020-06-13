package musin.seeker.vk.notifier;

import musin.seeker.telegram.api.TelegramMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class VkNotifierConfiguration {

  @Bean
  public VkUpdateNotifier vkConsoleUpdateNotifier() {
    return new VkConsoleUpdateNotifier();
  }

  @Bean
  @Profile("telegram")
  public VkUpdateNotifier vkTelegramUpdateNotifier(TelegramMessageSender sender) {
    return new VkTelegramUpdateNotifier(sender);
  }
}
