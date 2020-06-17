package musin.seeker.instagram.db;

import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.config.InstagramServiceProperties;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.instagram.relation.InstagramUser;
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
                                InstagramNotifiableUpdateFactory instagramNotifiableUpdateFactory,
                                InstagramServiceProperties instagramServiceProperties) {
    super(
        relationUpdateRepository,
        instagramNotifiableUpdateFactory,
        instagramServiceProperties);
  }

  @Override
  protected InstagramRelationList createList() {
    return new InstagramRelationList();
  }
}
