package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.Seeker;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class ScheduledSeeker {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<ChangesNotifier> notifiers;
  private final Executor executor = Executors.newFixedThreadPool(5);

  public void run() {
    for (Seeker seeker : seekerService.findAll())
      CompletableFuture.runAsync(() -> run(seeker.getOwner()), executor);
  }

  private void run(int owner) {
    CompletableFuture<RelationList> now = vkApi.loadRelationsAsync(owner);
    RelationList was = new RelationList(owner, relationChangeService.findAllByOwner(owner));

    List<RelationChange> difference = was.getDifferences(now.join());
    relationChangeService.saveAll(difference);
    for (ChangesNotifier notifier : notifiers) notifier.notify(difference);
  }
}