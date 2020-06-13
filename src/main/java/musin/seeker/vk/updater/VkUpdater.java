package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.RelationChangeService;
import musin.seeker.db.SeekerService;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.api.VkRelationListFactory;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkRelationFactory;
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
  private final List<VkUpdateNotifier> notifiers;
  private final TaskExecutor taskExecutor;
  private final VkUpdateFactory vkUpdateFactory;
  private final VkRelationFactory vkRelationFactory;
  private final VkRelationListFactory vkRelationListFactory;

  private static List<RelationChange> toDbChanges(int owner, Stream<VkRelationUpdate> changes) {
    return changes.map(update -> update.toDb(owner)).collect(toList());
  }

  @Override
  public void run() {
    seekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<VkRelationList> was = relationChangeService.findAllByOwner(owner)
        .thenApply(this::toVkRelations)
        .thenApply(VkRelationList::new);

    CompletableFuture<VkRelationList> now = vkRelationListFactory.create(owner);

    was.thenCombine(now, VkRelationList::updates)
        .thenApply(updates -> relationChangeService.saveAll(toDbChanges(owner, updates)))
        .thenApply(this::toVkUpdates)
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }

  private Stream<VkRelation> toVkRelations(List<RelationChange> changes) {
    return changes.stream()
        .map(c -> vkRelationFactory.create(c.getTarget(), c.getCurType()));
  }

  private List<VkUpdate> toVkUpdates(List<RelationChange> changes) {
    return changes.stream()
        .map(vkUpdateFactory::create)
        .collect(toList());
  }
}
