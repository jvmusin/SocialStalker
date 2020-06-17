package musin.seeker.instagram.updater;

import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.config.InstagramConfigurationProperties;
import musin.seeker.instagram.db.InstagramSeekerService;
import musin.seeker.instagram.db.InstagramUpdateService;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.notifier.InstagramUpdateNotifier;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.instagram.relation.InstagramUser;
import musin.seeker.updater.PeriodicUpdaterBase;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("instagram")
public class InstagramUpdater extends PeriodicUpdaterBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramUpdate,
    InstagramRelationList,
    InstagramNotifiableUpdate> {
  public InstagramUpdater(InstagramSeekerService seekerService,
                          InstagramUpdateService updateService,
                          InstagramRelationListPuller relationListPuller,
                          List<InstagramUpdateNotifier> updateNotifiers,
                          TaskExecutor taskExecutor,
                          InstagramConfigurationProperties config) {
    super(seekerService,
        updateService,
        relationListPuller,
        updateNotifiers,
        taskExecutor,
        config.getPeriodBetweenUpdates());
  }
}
