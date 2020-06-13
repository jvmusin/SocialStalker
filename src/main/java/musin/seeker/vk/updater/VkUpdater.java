package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.RelationChangeService;
import musin.seeker.db.SeekerService;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkNotifiableUpdateFactory;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.*;
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
  private final VkUserFactory vkUserFactory;
  private final VkRelationListFactory vkRelationListFactory;
  private final VkNotifiableUpdateFactory vkNotifiableUpdateFactory;

  private static List<RelationChange> toRelationChanges(int owner, Stream<VkUpdate> updates) {
    return updates.map(update -> update.toRelationChange(owner)).collect(toList());
  }

  @Override
  public void run() {
    seekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<VkRelationList> was = relationChangeService.findAllByOwner(owner)
        .thenApply(this::toVkRelations)
        .thenApply(vkRelationListFactory::create);

    CompletableFuture<VkRelationList> now = vkRelationListFactory.queryFromVk(owner);

    was.thenCombine(now, VkRelationList::updates)
        .thenApply(updates -> relationChangeService.saveAll(toRelationChanges(owner, updates)))
        .thenApply(this::toVkNotifiableUpdates)
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }

  private Stream<VkRelation> toVkRelations(List<RelationChange> changes) {
    return changes.stream()
        .map(c -> new VkRelation(vkUserFactory.create(c.getTarget()), c.getCurType()));
  }

  private List<VkNotifiableUpdate> toVkNotifiableUpdates(List<RelationChange> changes) {
    return changes.stream()
        .map(vkNotifiableUpdateFactory::create)
        .collect(toList());
  }
}
