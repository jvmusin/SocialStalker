package musin.seeker.vk.notifier;

import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;

public class VkTelegramUpdateNotifier extends TelegramUpdateNotifier<VkNotifiableUpdate> implements VkUpdateNotifier {
  public VkTelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }
}
