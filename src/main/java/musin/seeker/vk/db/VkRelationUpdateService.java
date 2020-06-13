package musin.seeker.vk.db;

import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkRelationUpdateService {
  CompletableFuture<List<VkNotifiableUpdate>> findAllByOwner(int owner);

  default CompletableFuture<VkRelationList> buildList(int owner) {
    return findAllByOwner(owner).thenApply(VkRelationList::new);
  }

  List<VkNotifiableUpdate> saveAll(List<? extends VkUpdate> updates, int owner);
}
