package musin.seeker.vk.db;

import musin.seeker.vk.notifier.VkNotifiableRelationUpdate;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkRelationUpdateService {
  CompletableFuture<List<VkNotifiableRelationUpdate>> findAllByOwner(int owner);

  default CompletableFuture<VkRelationList> buildList(int owner) {
    return findAllByOwner(owner).thenApply(VkRelationList::new);
  }

  List<VkNotifiableRelationUpdate> saveAll(List<? extends VkRelationUpdate> updates, int owner);
}
