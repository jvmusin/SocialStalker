package musin.seeker.telegram.notifier;

import musin.seeker.notifier.MarkdownUpdateNotifier;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.telegram.api.TelegramMessageSender;

public class TelegramUpdateNotifier<TNotifiableUpdate extends NotifiableUpdate<?, ?>>
    extends MarkdownUpdateNotifier<TNotifiableUpdate>
    implements UpdateNotifier<TNotifiableUpdate> {
  // todo add receiver (stalker)
  // todo create factory
  public TelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }
}
