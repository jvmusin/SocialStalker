package musin.socialstalker.vk.notifier;

import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkUpdate;

public interface VkNotifiableUpdate extends NotifiableUpdate<VkRelationType>, VkUpdate {
}
