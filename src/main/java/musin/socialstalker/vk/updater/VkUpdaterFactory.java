package musin.socialstalker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.MessageSender;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.updater.Updater;
import musin.socialstalker.updater.UpdaterFactory;
import musin.socialstalker.updater.UpdaterImpl;
import musin.socialstalker.vk.config.VkConfigurationProperties;
import musin.socialstalker.vk.db.VkMonitoringServiceFactory;
import musin.socialstalker.vk.db.VkUpdateServiceFactory;
import musin.socialstalker.vk.relation.VkRelationType;
import musin.socialstalker.vk.relation.VkUpdateFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class VkUpdaterFactory implements UpdaterFactory {

  private final VkMonitoringServiceFactory monitoringServiceFactory;
  private final VkUpdateServiceFactory updateServiceFactory;
  private final VkRelationListPuller relationListPuller;
  private final List<UpdateNotifierFactory<VkRelationType>> notifierFactories;
  private final TaskExecutor taskExecutor;
  private final VkConfigurationProperties config;
  private final VkUpdateFactory updateFactory;
  private final MessageSender adminMessageSender;

  private UpdateNotifier<VkRelationType> getAdminNotifier(Stalker stalker) {
    return update -> adminMessageSender.sendMessage(
        "FOR ADMIN FROM " + stalker + lineSeparator() + update.toMultilineMarkdownString()
    );
  }

  @Override
  public Updater create(Stalker stalker) {
    List<UpdateNotifier<VkRelationType>> notifiers = notifierFactories.stream()
        .map(f -> f.create(stalker))
        .collect(toList());
    notifiers.add(getAdminNotifier(stalker));
    return new UpdaterImpl<>(
        monitoringServiceFactory.create(stalker),
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
