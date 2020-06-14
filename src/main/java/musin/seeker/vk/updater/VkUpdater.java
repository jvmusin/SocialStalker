package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.vk.db.VkRelationUpdateService;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationListPuller;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkUpdater implements Runnable {

  private final VkSeekerService vkSeekerService;
  private final VkRelationUpdateService vkRelationUpdateService;
  private final List<VkUpdateNotifier> notifiers;
  private final TaskExecutor taskExecutor;
  private final VkRelationListPuller vkRelationListPuller;

  @Override
  public void run() {
    vkSeekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<VkRelationList> was = vkRelationUpdateService.buildList(owner);

    CompletableFuture<VkRelationList> now = vkRelationListPuller.pull(owner);

    was.thenCombine(now, VkRelationList::updates)
        .thenApply(u -> u.collect(toList()))
        .thenApply(updates -> vkRelationUpdateService.saveAll(updates, owner))
        .thenAccept(updates -> notifiers.forEach(notifier -> notifier.notify(updates)))
        .exceptionally(e -> {
          e.printStackTrace();
          return null;
        });
  }
}
