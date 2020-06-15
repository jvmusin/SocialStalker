package musin.seeker.vk.updater;

import lombok.Getter;
import musin.seeker.updater.BasicUpdater;
import musin.seeker.vk.config.VkConfigurationProperties;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.db.VkUpdateService;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkUpdateNotifier;
import musin.seeker.vk.relation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class VkUpdater extends BasicUpdater<VkID, VkUser, VkRelationType, VkUpdate, VkRelationList, VkNotifiableUpdate> {
  @Getter
  private final Duration periodBetweenUpdates;

  public VkUpdater(VkSeekerService seekerService,
                   VkUpdateService updateService,
                   VkRelationListPuller relationListPuller,
                   List<VkUpdateNotifier> updateNotifiers,
                   TaskExecutor taskExecutor,
                   VkConfigurationProperties config) {
    super(seekerService, updateService, relationListPuller, updateNotifiers, taskExecutor);
    periodBetweenUpdates = config.getPeriodBetweenUpdates();
  }
}
