package musin.socialstalker.instagram.updater;

import musin.socialstalker.instagram.api.InstagramApi;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.relation.*;
import musin.socialstalker.updater.RelationListPullerBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWING;

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
    registerQuery(api::getFollowers, FOLLOWER);
    registerQuery(api::getFollowing, FOLLOWING);
  }
}
