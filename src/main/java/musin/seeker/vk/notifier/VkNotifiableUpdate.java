package musin.seeker.vk.notifier;

import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkUpdate;
import musin.seeker.vk.relation.VkUser;

public interface VkNotifiableUpdate extends NotifiableUpdate<VkUser, VkRelationType>, VkUpdate {
}
