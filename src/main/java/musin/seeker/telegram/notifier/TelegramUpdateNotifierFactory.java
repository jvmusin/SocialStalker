package musin.seeker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.MarkdownUpdateNotifier;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.telegram.api.TelegramMessageSenderFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramUpdateNotifierFactory<TNotifiableUpdate extends NotifiableUpdate<?, ?>>
    implements UpdateNotifierFactory<TNotifiableUpdate> {

  private final TelegramMessageSenderFactory messageSenderFactory;

  @Override
  public UpdateNotifier<TNotifiableUpdate> create(Stalker stalker) {
    return new MarkdownUpdateNotifier<>(messageSenderFactory.create(stalker));
  }
}
