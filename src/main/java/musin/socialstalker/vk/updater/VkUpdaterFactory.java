package musin.socialstalker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.MessageSender;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.updater.Updater;
import musin.socialstalker.updater.UpdaterBase;
import musin.socialstalker.updater.UpdaterFactory;
import musin.socialstalker.vk.config.VkConfigurationProperties;
import musin.socialstalker.vk.db.VkSeekerServiceFactory;
import musin.socialstalker.vk.db.VkUpdateServiceFactory;
import musin.socialstalker.vk.notifier.VkNotifiableUpdate;
import musin.socialstalker.vk.relation.VkUpdateFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class VkUpdaterFactory implements UpdaterFactory {

  private final VkSeekerServiceFactory seekerServiceFactory;
  private final VkUpdateServiceFactory updateServiceFactory;
  private final VkRelationListPuller relationListPuller;
  private final List<UpdateNotifierFactory<VkNotifiableUpdate>> notifierFactories;
  private final TaskExecutor taskExecutor;
  private final VkConfigurationProperties config;
  private final VkUpdateFactory updateFactory;
  private final MessageSender adminMessageSender;

  private UpdateNotifier<VkNotifiableUpdate> getAdminMessageSender(Stalker stalker) {
    return update -> adminMessageSender.sendMessage(stalker + "\n" + update.toMultilineMarkdownString());
  }

  @Override
  public Updater create(Stalker stalker) {
    List<UpdateNotifier<VkNotifiableUpdate>> notifiers = notifierFactories.stream()
        .map(f -> f.create(stalker))
        .collect(toList());
    notifiers.add(getAdminMessageSender(stalker));
    return new UpdaterBase<>(
        seekerServiceFactory.create(stalker),
        updateServiceFactory.create(stalker),
        relationListPuller,
        notifiers,
        taskExecutor,
        config.getPeriodBetweenUpdates(),
        updateFactory
    );
  }
}
