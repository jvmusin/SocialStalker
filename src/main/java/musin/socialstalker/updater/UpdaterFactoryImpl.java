package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.Id;
import musin.socialstalker.config.UpdaterConfig;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.telegram.api.AdminMessageSender;
import org.springframework.core.task.TaskExecutor;

import java.time.Duration;
import java.util.List;

import static java.lang.System.lineSeparator;
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
