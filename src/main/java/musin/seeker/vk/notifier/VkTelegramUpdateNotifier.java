package musin.seeker.vk.notifier;

import musin.seeker.telegram.api.TelegramMessageSender;
import musin.seeker.telegram.notifier.TelegramUpdateNotifier;
import musin.seeker.vk.updater.VkRelation;
import musin.seeker.vk.updater.VkUpdate;

public class VkTelegramUpdateNotifier extends TelegramUpdateNotifier<VkRelation, VkUpdate> implements VkUpdateNotifier {
  public VkTelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }
}
