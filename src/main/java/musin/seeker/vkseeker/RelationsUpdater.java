package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.notifiers.ChangesNotifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //todo make it async
    RelationList was = new RelationList(owner, relationChangeService.findAllByOwner(owner));

    vkApi.loadRelationsAsync(owner)
        .thenApply(was::getDifferences)
        .thenApply(relationChangeService::saveAll)
        .thenAccept(differences -> notifiers.forEach(notifier -> notifier.notify(differences)));
  }
}
