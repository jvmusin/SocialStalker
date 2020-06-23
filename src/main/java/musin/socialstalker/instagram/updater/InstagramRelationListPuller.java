package musin.socialstalker.instagram.updater;

import musin.socialstalker.instagram.api.InstagramApi;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.RelationListPullerBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWING;

@Component
@Profile("instagram")
public class InstagramRelationListPuller extends RelationListPullerBase<InstagramID, InstagramRelationType> {
  public InstagramRelationListPuller(RelationListFactory<InstagramRelationType> relationListFactory,
                                     UpdateFactory<InstagramRelationType> updateFactory,
                                     RelationFactory<InstagramRelationType> relationFactory,
                                     UserFactory<InstagramID> userFactory,
                                     InstagramApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::getFollowers, FOLLOWER);
    registerQuery(api::getFollowing, FOLLOWING);
  }
}
