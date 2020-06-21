package musin.socialstalker.telegram.api;

import musin.socialstalker.telegram.config.TelegramConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class AdminMessageSender extends TelegramMessageSenderImpl {
  public AdminMessageSender(TelegramConfigurationProperties config, AbsSender telegramAbsSender) {
    super(config.getAdminUid(), telegramAbsSender);
  }
}
