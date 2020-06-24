package musin.socialstalker.vk.updater;

import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.telegram.api.AdminMessageSender;
import musin.socialstalker.updater.UpdaterFactoryImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkConfigurationProperties;
import musin.socialstalker.vk.db.VkMonitoringServiceFactory;
import musin.socialstalker.vk.db.VkUpdateServiceFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VkUpdaterFactory extends UpdaterFactoryImpl<VkID> {

  public VkUpdaterFactory(VkMonitoringServiceFactory monitoringServiceFactory,
                          VkUpdateServiceFactory updateServiceFactory,
                          VkRelationListPuller relationListPuller,
                          List<UpdateNotifierFactory> notifierFactories,
                          TaskExecutor taskExecutor,
                          VkConfigurationProperties config,
                          UpdateFactory updateFactory,
                          AdminMessageSender adminMessageSender) {
    super(
        monitoringServiceFactory,
        updateServiceFactory,
        relationListPuller,
        notifierFactories,
        taskExecutor,
        config,
        updateFactory,
        adminMessageSender
    );
  }
}
