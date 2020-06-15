package musin.seeker.vk.db;

import musin.seeker.updater.UpdateService;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkID;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkUpdate;

import java.util.concurrent.CompletableFuture;

public interface VkUpdateService extends UpdateService<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> {
  default CompletableFuture<VkRelationList> buildList(VkID owner) {
    return findAllByOwner(owner).thenApply(VkRelationList::new);
  }
}
