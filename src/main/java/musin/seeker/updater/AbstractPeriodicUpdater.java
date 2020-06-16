package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.relation.RelationList;
import musin.seeker.relation.User;
import org.springframework.core.task.TaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class AbstractPeriodicUpdater<
    ID,
    TUser extends User<?>,
    TRelationType,
    TUpdate,
    TRelationList extends RelationList<TUser, TRelationType, ?, TUpdate>,
    TNotifiableUpdate extends TUpdate>
    implements PeriodicUpdater {

  private final SeekerService<ID> seekerService;
  private final UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> updateService;
  private final RelationListPuller<ID, TRelationList> relationListPuller;
  private final List<? extends UpdateNotifier<? super TNotifiableUpdate>> notifiers;
  private final TaskExecutor taskExecutor;

  @Override
  public void run() {
    seekerService.findAllOwners().forEach(s -> taskExecutor.execute(() -> run(s)));
  }

  private void run(ID owner) {
    CompletableFuture<TRelationList> was = updateService.buildList(owner);

    CompletableFuture<TRelationList> now = relationListPuller.pull(owner);

    was.thenCombine(now, RelationList::updates)
        .thenApply(updates -> updates.collect(toList()))
        .thenApply(updates -> updateService.saveAll(updates, owner))
        .thenAccept(updates -> notifiers.forEach(notifier -> notifier.notify(updates)))
        .exceptionally(e -> {
          e.printStackTrace();
          return null;
        });
  }
}
