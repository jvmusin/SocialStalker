package musin.seeker.telegram.config;

import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.api.TelegramMessageSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Configuration
@Profile("telegram")
public class TelegramConfig {
  @Bean
  public TelegramMessageSender sender(TelegramConfigurationProperties config, AbsSender telegramAbsSender) {
    return new TelegramMessageSenderImpl(config.getReceiverUid(), telegramAbsSender);
  }
}
