package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.config.UpdaterConfig;
import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.UpdateNotifierFactory;
import musin.stalker.relation.UpdateFactory;
import org.springframework.core.task.TaskExecutor;

import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class UpdaterFactoryImpl<ID extends Id> implements UpdaterFactory {

  private final MonitoringServiceFactory<ID> monitoringServiceFactory;
  private final UpdateServiceFactory updateServiceFactory;
  private final RelationListPuller<ID> relationListPuller;
  private final List<UpdateNotifierFactory> notifierFactories;
  private final TaskExecutor taskExecutor;
  private final UpdaterConfig config;
  private final UpdateFactory updateFactory;

  @Override
  public Updater create(Stalker stalker) {
    return new UpdaterImpl<>(
        monitoringServiceFactory.create(stalker),
        updateServiceFactory.create(stalker),
        relationListPuller,
        notifierFactories.stream()
            .map(f -> f.create(stalker))
            .collect(toList()),
        taskExecutor,
        updateFactory
    );
  }

  @Override
  public Duration getPeriodBetweenUpdates() {
    return config.getPeriodBetweenUpdates();
  }
}
