package musin.stalker.telegram.notifier;

import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.MarkdownUpdateNotifier;
import musin.stalker.notifier.MessageSenderFactory;
import musin.stalker.notifier.UpdateNotifier;
import musin.stalker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class TelegramUpdateNotifierFactory implements UpdateNotifierFactory {
  private final MessageSenderFactory messageSenderFactory;

  public TelegramUpdateNotifierFactory(MessageSenderFactory messageSenderFactory) {
    this.messageSenderFactory = messageSenderFactory;
  }

  @Override
  public UpdateNotifier create(Stalker stalker) {
    return new MarkdownUpdateNotifier(messageSenderFactory.create(stalker));
  }
}
