package musin.seeker.vkseeker.vk.notifier;

import musin.seeker.vkseeker.notifier.ConsoleUpdateNotifier;
import musin.seeker.vkseeker.notifier.UpdateNotifier;
import musin.seeker.vkseeker.telegram.TelegramMessageSender;
import musin.seeker.vkseeker.telegram.TelegramUpdateNotifier;
import musin.seeker.vkseeker.vk.updater.VkUpdate;
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
