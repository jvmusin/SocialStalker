package musin.seeker.instagram.updater;

import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.relation.*;
import musin.seeker.updater.RelationListPullerBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static musin.seeker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.seeker.instagram.relation.InstagramRelationType.FOLLOWING;

@Component
@Profile("instagram")
public class InstagramRelationListPuller extends RelationListPullerBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramRelation,
    InstagramUpdate,
    InstagramRelationList> {
  public InstagramRelationListPuller(InstagramRelationListFactory relationListFactory,
                                     InstagramUpdateFactory updateFactory,
                                     InstagramRelationFactory relationFactory,
                                     InstagramUserFactory userFactory,
                                     InstagramApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::loadFollowers, FOLLOWER);
    registerQuery(api::loadFollowing, FOLLOWING);
  }
}
