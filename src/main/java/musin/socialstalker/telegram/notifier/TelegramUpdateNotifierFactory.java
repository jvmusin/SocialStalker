package musin.socialstalker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.MarkdownUpdateNotifier;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.api.TelegramMessageSenderFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramUpdateNotifierFactory<TNotifiableUpdate extends NotifiableUpdate<?>>
    implements UpdateNotifierFactory<TNotifiableUpdate> {

  private final TelegramMessageSenderFactory messageSenderFactory;

  @Override
  public UpdateNotifier<TNotifiableUpdate> create(Stalker stalker) {
    return new MarkdownUpdateNotifier<>(messageSenderFactory.create(stalker));
  }
}
