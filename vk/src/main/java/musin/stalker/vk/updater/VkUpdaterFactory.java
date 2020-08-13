package musin.stalker.vk.updater;

import musin.stalker.notifier.UpdateNotifierFactory;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.vk.api.VkID;
import musin.stalker.vk.db.VkMonitoringServiceFactory;
import musin.stalker.vk.db.VkUpdateServiceFactory;
import musin.stalker.updater.UpdaterFactoryImpl;
import musin.stalker.vk.config.VkConfigurationProperties;
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
                          UpdateFactory updateFactory) {
    super(
        monitoringServiceFactory,
        updateServiceFactory,
        relationListPuller,
        notifierFactories,
        taskExecutor,
        config,
        updateFactory
    );
  }
}
