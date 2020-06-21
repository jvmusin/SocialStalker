package musin.seeker.vk.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.telegram.notifier.TelegramUpdateNotifierFactory;
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
