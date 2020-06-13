package musin.seeker.vk.notifier;

import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkUpdate;
import musin.seeker.vk.relation.VkUser;

public interface VkNotifiableUpdate extends NotifiableUpdate<VkUser, VkRelation>, VkUpdate {
}
