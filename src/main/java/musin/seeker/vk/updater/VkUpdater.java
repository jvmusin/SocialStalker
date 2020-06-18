package musin.seeker.vk.updater;

import musin.seeker.updater.PeriodicUpdaterBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.config.VkConfigurationProperties;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.db.VkUpdateService;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VkUpdater extends PeriodicUpdaterBase<
    VkID,
    VkUser,
    VkRelationType,
    VkUpdate,
    VkRelationList,
    VkNotifiableUpdate> {
  public VkUpdater(VkSeekerService seekerService,
                   VkUpdateService updateService,
                   VkRelationListPuller relationListPuller,
                   List<VkUpdateNotifier> updateNotifiers,
                   TaskExecutor taskExecutor,
                   VkConfigurationProperties config,
                   VkUpdateFactory updateFactory) {
    super(seekerService,
        updateService,
        relationListPuller,
        updateNotifiers,
        taskExecutor,
        config.getPeriodBetweenUpdates(),
        updateFactory);
  }
}
