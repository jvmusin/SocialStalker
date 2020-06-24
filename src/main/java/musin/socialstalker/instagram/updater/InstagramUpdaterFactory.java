package musin.socialstalker.instagram.updater;

import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramConfigurationProperties;
import musin.socialstalker.instagram.db.InstagramMonitoringServiceFactory;
import musin.socialstalker.instagram.db.InstagramUpdateServiceFactory;
import musin.socialstalker.instagram.relation.InstagramUpdateFactory;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.telegram.api.AdminMessageSender;
import musin.socialstalker.updater.UpdaterFactoryImpl;
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
      InstagramUpdateFactory updateFactory,
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
