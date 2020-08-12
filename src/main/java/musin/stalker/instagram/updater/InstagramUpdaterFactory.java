package musin.stalker.instagram.updater;

import musin.stalker.instagram.api.InstagramID;
import musin.stalker.instagram.config.InstagramConfigurationProperties;
import musin.stalker.instagram.db.InstagramMonitoringServiceFactory;
import musin.stalker.instagram.db.InstagramUpdateServiceFactory;
import musin.stalker.notifier.UpdateNotifierFactory;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.updater.UpdaterFactoryImpl;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstagramUpdaterFactory extends UpdaterFactoryImpl<InstagramID> {
  public InstagramUpdaterFactory(
      InstagramMonitoringServiceFactory monitoringServiceFactory,
      InstagramUpdateServiceFactory updateServiceFactory,
      InstagramRelationListPuller relationListPuller,
      List<UpdateNotifierFactory> notifierFactories,
      TaskExecutor taskExecutor,
      InstagramConfigurationProperties config,
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
