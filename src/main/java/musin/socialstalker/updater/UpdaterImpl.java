package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.list.RelationList;
import org.apache.logging.log4j.Level;
import org.springframework.core.task.TaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Log4j2
@RequiredArgsConstructor
public class UpdaterImpl<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TRelationType>,
    TRelationList extends RelationList<TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TRelationType>>
    implements Updater {

  private final MonitoringService<ID> monitoringService;
  private final UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> updateService;
  private final RelationListPuller<ID, TRelationList> relationListPuller;
  private final List<? extends UpdateNotifier<? super TNotifiableUpdate>> notifiers;
  private final TaskExecutor taskExecutor;
  private final UpdateFactory<TRelationType, TUpdate> updateFactory;

  @Override
  public void run() {
    monitoringService.findAllTargets().forEach(s -> taskExecutor.execute(() -> run(s)));
  }

  private void run(ID target) {
    CompletableFuture<TRelationList> was = updateService.buildList(target);

    CompletableFuture<TRelationList> now = relationListPuller.pull(target);

    was.thenCombine(now, (a, b) -> a.updates(b, updateFactory))
        .thenApply(updates -> updates.collect(toList()))
        .thenApply(updates -> updateService.saveAll(updates, target))
        .thenAccept(updates -> notifiers.forEach(notifier -> notifier.notify(updates)))
        .exceptionally(e -> {
          log.throwing(Level.ERROR, e);
          return null;
        });
  }
}
