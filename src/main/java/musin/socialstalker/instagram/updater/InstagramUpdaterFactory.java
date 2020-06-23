package musin.socialstalker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.config.InstagramConfigurationProperties;
import musin.socialstalker.instagram.db.InstagramMonitoringServiceFactory;
import musin.socialstalker.instagram.db.InstagramUpdateServiceFactory;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdateFactory;
import musin.socialstalker.notifier.MessageSender;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.updater.Updater;
import musin.socialstalker.updater.UpdaterFactory;
import musin.socialstalker.updater.UpdaterImpl;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstagramUpdaterFactory implements UpdaterFactory {

  private final InstagramMonitoringServiceFactory seekerServiceFactory;
  private final InstagramUpdateServiceFactory updateServiceFactory;
  private final InstagramRelationListPuller relationListPuller;
  private final List<UpdateNotifierFactory<InstagramNotifiableUpdate, InstagramRelationType>> notifierFactories;
  private final TaskExecutor taskExecutor;
  private final InstagramConfigurationProperties config;
  private final InstagramUpdateFactory updateFactory;
  private final MessageSender adminMessageSender;

  private UpdateNotifier<InstagramNotifiableUpdate, InstagramRelationType> getAdminMessageSender(Stalker stalker) {
    return update -> adminMessageSender.sendMessage(
        "FOR ADMIN FROM " + stalker + lineSeparator() + update.toMultilineMarkdownString()
    );
  }

  @Override
  public Updater create(Stalker stalker) {
    List<UpdateNotifier<InstagramNotifiableUpdate, InstagramRelationType>> notifiers = notifierFactories.stream()
        .map(f -> f.create(stalker))
        .collect(toList());
    notifiers.add(getAdminMessageSender(stalker));
    return new UpdaterImpl<>(
        seekerServiceFactory.create(stalker),
        updateServiceFactory.create(stalker),
        relationListPuller,
        notifiers,
        taskExecutor,
        updateFactory
    );
  }

  @Override
  public Duration getPeriodBetweenUpdates() {
    return config.getPeriodBetweenUpdates();
  }
}
