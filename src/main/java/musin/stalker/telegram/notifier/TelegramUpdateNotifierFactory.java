package musin.stalker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.MarkdownUpdateNotifier;
import musin.stalker.notifier.UpdateNotifier;
import musin.stalker.notifier.UpdateNotifierFactory;
import musin.stalker.telegram.api.TelegramMessageSenderFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramUpdateNotifierFactory implements UpdateNotifierFactory {
  private final TelegramMessageSenderFactory messageSenderFactory;

  @Override
  public UpdateNotifier create(Stalker stalker) {
    return new MarkdownUpdateNotifier(messageSenderFactory.create(stalker));
  }
}
