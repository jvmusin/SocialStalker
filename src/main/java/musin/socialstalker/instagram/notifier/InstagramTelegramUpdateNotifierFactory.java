package musin.socialstalker.instagram.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.notifier.TelegramUpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramTelegramUpdateNotifierFactory implements UpdateNotifierFactory<InstagramNotifiableUpdate> {
  private final TelegramUpdateNotifierFactory<InstagramNotifiableUpdate> updateNotifierFactory;

  @Override
  public UpdateNotifier<InstagramNotifiableUpdate> create(Stalker stalker) {
    return updateNotifierFactory.create(stalker);
  }
}
