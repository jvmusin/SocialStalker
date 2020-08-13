package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musin.stalker.db.Id;
import musin.stalker.notifier.UpdateNotifier;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.relation.list.RelationList;
import org.springframework.core.task.TaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class UpdaterImpl<ID extends Id> implements Updater {

  private final MonitoringService<ID> monitoringService;
  private final UpdateService updateService;
  private final RelationListPuller<ID> relationListPuller;
  private final List<UpdateNotifier> notifiers;
  private final TaskExecutor taskExecutor;
  private final UpdateFactory updateFactory;

  @Override
  public void run() {
    monitoringService.findAllTargets().forEach(s -> taskExecutor.execute(() -> run(s)));
  }

  private void run(ID target) {
    CompletableFuture<RelationList> was = updateService.buildList(target);

    CompletableFuture<RelationList> now = relationListPuller.pull(target);

    was.thenCombine(now, (a, b) -> a.updates(b, updateFactory))
        .thenApply(updates -> updates.collect(toList()))
        .thenApply(updates -> updateService.saveAll(updates, target))
        .thenAccept(updates -> notifiers.forEach(notifier -> notifier.notify(updates)))
        .exceptionally(e -> {
          log.error("Updating relation list failed", e);
          return null;
        });
  }
}
