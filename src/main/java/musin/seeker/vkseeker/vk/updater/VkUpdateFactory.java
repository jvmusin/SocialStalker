package musin.seeker.vkseeker.vk.updater;

import musin.seeker.vkseeker.db.model.RelationChange;

public interface VkUpdateFactory {
  VkUpdate createUpdate(RelationChange change);
}
