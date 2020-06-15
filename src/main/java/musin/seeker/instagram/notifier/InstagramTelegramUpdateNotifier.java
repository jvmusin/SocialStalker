package musin.seeker.instagram.notifier;

import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("telegram")
public class InstagramTelegramUpdateNotifier extends TelegramUpdateNotifier<InstagramNotifiableUpdate> implements InstagramUpdateNotifier {
  public InstagramTelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }

  @Override
  protected int getMinSizeForABunchOfChanges() {
    return Integer.MAX_VALUE;
  }
}
