package musin.stalker.telegram.api;

import lombok.RequiredArgsConstructor;
import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.MessageSender;
import musin.stalker.notifier.MessageSenderFactory;
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
