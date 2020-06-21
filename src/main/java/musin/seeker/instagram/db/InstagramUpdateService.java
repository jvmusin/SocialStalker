package musin.seeker.instagram.db;

import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.config.InstagramNetworkProperties;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.seeker.instagram.relation.*;
import musin.seeker.updater.UpdateServiceBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("instagram")
public class InstagramUpdateService extends UpdateServiceBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramUpdate,
    InstagramRelationList,
    InstagramNotifiableUpdate> {

  public InstagramUpdateService(RelationUpdateRepository relationUpdateRepository,
                                InstagramNotifiableUpdateFactory notifiableUpdateFactory,
                                InstagramNetworkProperties networkProperties,
                                InstagramRelationListFactory relationListFactory) {
    super(
        relationUpdateRepository,
        notifiableUpdateFactory,
        networkProperties,
        relationListFactory);
  }
}
