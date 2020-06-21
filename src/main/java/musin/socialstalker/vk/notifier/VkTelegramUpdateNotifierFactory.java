package musin.socialstalker.vk.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.notifier.TelegramUpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkTelegramUpdateNotifierFactory implements UpdateNotifierFactory<VkNotifiableUpdate> {
  private final TelegramUpdateNotifierFactory<VkNotifiableUpdate> updateNotifierFactory;

  @Override
  public UpdateNotifier<VkNotifiableUpdate> create(Stalker stalker) {
    return updateNotifierFactory.create(stalker);
  }
}
