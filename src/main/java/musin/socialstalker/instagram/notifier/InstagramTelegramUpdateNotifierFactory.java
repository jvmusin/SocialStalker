package musin.socialstalker.instagram.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.telegram.notifier.TelegramUpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramTelegramUpdateNotifierFactory implements InstagramUpdateNotifierFactory {
  private final TelegramUpdateNotifierFactory updateNotifierFactory;

  @Override
  public UpdateNotifier create(Stalker stalker) {
    return updateNotifierFactory.create(stalker);
  }
}
