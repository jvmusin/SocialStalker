package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.RelationChangeService;
import musin.seeker.db.SeekerService;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationUpdate;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkUpdater implements Runnable {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<VkUpdateNotifier> notifiers;
  private final TaskExecutor taskExecutor;
  private final VkUpdateFactory vkUpdateFactory;

  private static List<RelationChange> toDbChanges(int owner, Stream<VkRelationUpdate> changes) {
    return changes.map(update -> update.toDb(owner)).collect(toList());
  }

  @Override
  public void run() {
    seekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<VkRelationList> was = relationChangeService.findAllByOwner(owner)
        .thenApply(changes -> changes.stream().map(VkRelation::fromDb))
        .thenApply(VkRelationList::new);

    CompletableFuture<VkRelationList> now = vkApi.loadRelationsAsync(owner);

    was.thenCombine(now, VkRelationList::updates)
        .thenApply(updates -> relationChangeService.saveAll(toDbChanges(owner, updates)))
        .thenApply(this::toVkUpdates)
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }

  private List<VkUpdate> toVkUpdates(List<RelationChange> changes) {
    return changes.stream()
        .map(vkUpdateFactory::createUpdate)
        .collect(toList());
  }
}
