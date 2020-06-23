package musin.socialstalker.vk.notifier;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.notifier.TelegramUpdateNotifierFactory;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkTelegramUpdateNotifierFactory implements UpdateNotifierFactory<VkRelationType> {
  private final TelegramUpdateNotifierFactory<VkNotifiableUpdate, VkRelationType> updateNotifierFactory;

  @Override
  public UpdateNotifier<VkRelationType> create(Stalker stalker) {
    return updateNotifierFactory.create(stalker);
  }
}
