package musin.socialstalker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.config.InstagramConfigurationProperties;
import musin.socialstalker.instagram.db.InstagramSeekerServiceFactory;
import musin.socialstalker.instagram.db.InstagramUpdateServiceFactory;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramUpdateFactory;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.updater.Updater;
import musin.socialstalker.updater.UpdaterBase;
import musin.socialstalker.updater.UpdaterFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
        notifierFactories.stream().map(f -> f.create(stalker)).collect(toList()),
        taskExecutor,
        config.getPeriodBetweenUpdates(),
        updateFactory
    );
  }
}
