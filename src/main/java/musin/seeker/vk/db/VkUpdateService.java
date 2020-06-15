package musin.seeker.vk.db;

import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkID;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkUpdateService {
  CompletableFuture<List<VkNotifiableUpdate>> findAllByOwner(VkID owner);

  default CompletableFuture<VkRelationList> buildList(VkID owner) {
    return findAllByOwner(owner).thenApply(VkRelationList::new);
  }

  List<VkNotifiableUpdate> saveAll(List<? extends VkUpdate> updates, VkID owner);
}
