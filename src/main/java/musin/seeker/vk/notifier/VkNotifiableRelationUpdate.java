package musin.seeker.vk.notifier;

import musin.seeker.notifier.NotifiableRelationUpdate;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkRelationUpdate;
import musin.seeker.vk.relation.VkUser;

public interface VkNotifiableRelationUpdate extends NotifiableRelationUpdate<VkUser, VkRelationType>, VkRelationUpdate {
}
