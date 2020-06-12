package musin.seeker.vk.updater;

import musin.seeker.db.model.RelationChange;

public interface VkUpdateFactory {
  VkUpdate createUpdate(RelationChange change);
}
