package musin.seeker.instagram.updater;

import lombok.Getter;
import musin.seeker.instagram.config.InstagramConfigurationProperties;
import musin.seeker.instagram.db.InstagramSeekerService;
import musin.seeker.instagram.db.InstagramUpdateService;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.notifier.InstagramUpdateNotifier;
import musin.seeker.instagram.relation.*;
import musin.seeker.updater.BasicUpdater;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@Profile("instagram")
public class InstagramUpdater extends BasicUpdater<InstagramID, InstagramUser, InstagramRelationType, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> {
  @Getter
  private final Duration periodBetweenUpdates;

  public InstagramUpdater(InstagramSeekerService seekerService,
                          InstagramUpdateService updateService,
                          InstagramRelationListPuller relationListPuller,
                          List<InstagramUpdateNotifier> updateNotifiers,
                          TaskExecutor taskExecutor,
                          InstagramConfigurationProperties config) {
    super(seekerService, updateService, relationListPuller, updateNotifiers, taskExecutor);
    periodBetweenUpdates = config.getPeriodBetweenUpdates();
  }
}
