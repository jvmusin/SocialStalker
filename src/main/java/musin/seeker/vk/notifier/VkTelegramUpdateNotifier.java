package musin.seeker.vk.notifier;

import musin.seeker.notifier.MarkdownUpdateNotifier;
import musin.seeker.telegram.api.TelegramMessageSender;

public class VkTelegramUpdateNotifier
    extends MarkdownUpdateNotifier<VkNotifiableUpdate>
    implements VkUpdateNotifier {
  public VkTelegramUpdateNotifier(TelegramMessageSender sender) {
    super(sender);
  }
}
