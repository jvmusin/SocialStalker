package musin.seeker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.notifier.MarkdownUpdateNotifier;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.User;
import musin.seeker.telegram.api.TelegramMessageSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class TelegramUpdateNotifier<
    TUpdate extends NotifiableUpdate<? extends User<?>, ?>>
    extends MarkdownUpdateNotifier<TUpdate> {

  private final TelegramMessageSender sender;

  @Override
  protected void sendMessage(@NotNull String message) {
    sender.sendMessage(message, false);
  }
}
