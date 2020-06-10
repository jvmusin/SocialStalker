package musin.seeker.vkseeker.vk.relation;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.notifier.ChangesNotifier;
import musin.seeker.vkseeker.vk.VkApi;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class VkRelationsUpdater implements Runnable {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<ChangesNotifier> notifiers;
  private final TaskExecutor taskExecutor;

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
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }
}
