package musin.seeker.vk.notifier;

import musin.seeker.vk.updater.VkUpdate;
import musin.seeker.notifier.ConsoleUpdateNotifier;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class VkNotifierConfiguration {

  @Bean
  public UpdateNotifier<VkUpdate> vkConsoleUpdateNotifier() {
    return new ConsoleUpdateNotifier<>();
  }

  @Bean
  @Profile("telegram")
  public UpdateNotifier<VkUpdate> vkTelegramUpdateNotifier(TelegramMessageSender sender) {
    return new TelegramUpdateNotifier<>(sender);
  }
}
