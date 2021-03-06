package musin.socialstalker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.MarkdownUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.api.TelegramMessageSenderFactory;
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
