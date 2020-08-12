package musin.stalker.telegram.api;

import musin.stalker.telegram.config.TelegramConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class AdminMessageSender extends TelegramMessageSenderImpl {
  public AdminMessageSender(TelegramConfigurationProperties config, AbsSender telegramAbsSender) {
    super(config.getAdminUid(), telegramAbsSender);
  }
}
