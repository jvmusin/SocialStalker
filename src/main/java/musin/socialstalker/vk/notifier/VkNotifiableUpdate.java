package musin.socialstalker.vk.notifier;

import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkUpdate;
import musin.socialstalker.vk.relation.VkUser;

public interface VkNotifiableUpdate extends NotifiableUpdate<VkUser, VkRelationType>, VkUpdate {
}
