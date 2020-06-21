package musin.seeker.vk.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkTelegramUpdateNotifierFactory implements UpdateNotifierFactory<VkNotifiableUpdate> {
  private final TelegramMessageSender sender;

  @Override
  public UpdateNotifier<VkNotifiableUpdate> create(Stalker stalker) {
    //todo use stalker with factory
    return new TelegramUpdateNotifier<>(sender);
  }
}
