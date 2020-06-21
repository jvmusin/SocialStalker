package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.UpdateNotifierFactory;
import musin.seeker.updater.Updater;
import musin.seeker.updater.UpdaterBase;
import musin.seeker.updater.UpdaterFactory;
import musin.seeker.vk.config.VkConfigurationProperties;
import musin.seeker.vk.db.VkSeekerServiceFactory;
import musin.seeker.vk.db.VkUpdateServiceFactory;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkUpdateFactory;
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
  private final List<UpdateNotifierFactory<VkNotifiableUpdate>> updateNotifierFactories;
  private final TaskExecutor taskExecutor;
  private final VkConfigurationProperties config;
  private final VkUpdateFactory updateFactory;

  @Override
  public Updater create(Stalker stalker) {
    return new UpdaterBase<>(
        seekerServiceFactory.create(stalker),
        updateServiceFactory.create(stalker),
        relationListPuller,
        updateNotifierFactories.stream().map(f -> f.create(stalker)).collect(toList()),
        taskExecutor,
        config.getPeriodBetweenUpdates(),
        updateFactory
    );
  }
}
