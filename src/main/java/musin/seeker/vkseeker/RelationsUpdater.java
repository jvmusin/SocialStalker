package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class RelationsUpdater {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<ChangesNotifier> notifiers;
  private final TaskExecutor taskExecutor;

  public void run() {
    seekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(int owner) {
    CompletableFuture<RelationList> now = vkApi.loadRelationsAsync(owner);
    RelationList was = new RelationList(owner, relationChangeService.findAllByOwner(owner));

    List<RelationChange> difference = was.getDifferences(now.join());
    relationChangeService.saveAll(difference);
    for (ChangesNotifier notifier : notifiers) notifier.notify(difference);
  }
}
