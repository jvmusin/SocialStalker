package musin.seeker.vkseeker.updater;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.notifier.ChangesNotifier;
import musin.seeker.vkseeker.vk.VkApi;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class RelationsUpdater implements Runnable {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<ChangesNotifier> notifiers;
  private final TaskExecutor taskExecutor;

  @Override
  public void run() {
    seekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<RelationList> was = relationChangeService.findAllByOwner(owner)
        .thenApply(changes -> new RelationList(owner, changes));
    CompletableFuture<RelationList> now = vkApi.loadRelationsAsync(owner);
    was.thenCombine(now, RelationList::getUpdates)
        .thenApply(relationChangeService::saveAll)
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }
}
