package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.instagram.config.InstagramConfigurationProperties;
import musin.seeker.instagram.db.InstagramSeekerServiceFactory;
import musin.seeker.instagram.db.InstagramUpdateServiceFactory;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.InstagramUpdateFactory;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.updater.Updater;
import musin.seeker.updater.UpdaterBase;
import musin.seeker.updater.UpdaterFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InstagramUpdaterFactory implements UpdaterFactory {

  private final InstagramSeekerServiceFactory seekerServiceFactory;
  private final InstagramUpdateServiceFactory updateServiceFactory;
  private final InstagramRelationListPuller relationListPuller;
  private final List<UpdateNotifierFactory<InstagramNotifiableUpdate>> notifierFactories;
  private final TaskExecutor taskExecutor;
  private final InstagramConfigurationProperties config;
  private final InstagramUpdateFactory updateFactory;

  @Override
  public Updater create(Stalker stalker) {
    return new UpdaterBase<>(
        seekerServiceFactory.create(stalker),
        updateServiceFactory.create(stalker),
        relationListPuller,
        notifierFactories.stream().map(f -> f.create(stalker)).collect(Collectors.toList()),
        taskExecutor,
        config.getPeriodBetweenUpdates(),
        updateFactory
    );
  }
}
