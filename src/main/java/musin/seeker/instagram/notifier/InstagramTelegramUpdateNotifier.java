package musin.seeker.instagram.notifier;

import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;
import org.springframework.stereotype.Component;

@Component
public class InstagramTelegramUpdateNotifier
    extends TelegramUpdateNotifier<InstagramNotifiableUpdate> {
  public InstagramTelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }
}
