package musin.socialstalker.instagram.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.notifier.TelegramUpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramTelegramUpdateNotifierFactory implements UpdateNotifierFactory<InstagramRelationType> {
  private final TelegramUpdateNotifierFactory<InstagramRelationType> updateNotifierFactory;

  @Override
  public UpdateNotifier<? super InstagramRelationType> create(Stalker stalker) {
    return updateNotifierFactory.create(stalker);
  }
}
