package musin.seeker.vkseeker;

import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.runAsync;

@Service
public class ScheduledSeeker {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final List<ChangesNotifier> notifiers;
  private final Executor executor;

  public ScheduledSeeker(SeekerService seekerService, RelationChangeService relationChangeService, VkApi vkApi, List<ChangesNotifier> notifiers, @Qualifier("MyTaskExecutor") Executor executor) {
    this.seekerService = seekerService;
    this.relationChangeService = relationChangeService;
    this.vkApi = vkApi;
    this.notifiers = notifiers;
    this.executor = executor;
  }

  public void run() {
    seekerService.findAll().forEach(s -> runAsync(() -> run(s.getOwner()), executor));
  }

  private void run(int owner) {
    CompletableFuture<RelationList> now = vkApi.loadRelationsAsync(owner);
    RelationList was = new RelationList(owner, relationChangeService.findAllByOwner(owner));

    List<RelationChange> difference = was.getDifferences(now.join());
    relationChangeService.saveAll(difference);
    for (ChangesNotifier notifier : notifiers) notifier.notify(difference);
  }
}
