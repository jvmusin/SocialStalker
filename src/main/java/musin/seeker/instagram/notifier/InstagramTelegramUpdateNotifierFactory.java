package musin.seeker.instagram.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.telegram.notifier.TelegramUpdateNotifierFactory;
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
