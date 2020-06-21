package musin.socialstalker.telegram.api;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.MessageSender;
import musin.socialstalker.notifier.MessageSenderFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class TelegramMessageSenderFactory implements MessageSenderFactory {
  private final AbsSender sender;

  @Override
  public MessageSender create(Stalker stalker) {
    return new TelegramMessageSenderImpl(stalker.getTelegramId(), sender);
  }
}
