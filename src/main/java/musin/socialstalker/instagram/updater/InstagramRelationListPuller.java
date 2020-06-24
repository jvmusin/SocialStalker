package musin.socialstalker.instagram.updater;

import musin.socialstalker.instagram.api.InstagramApi;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.relation.InstagramRelationListFactory;
import musin.socialstalker.instagram.relation.InstagramUserFactory;
import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.updater.RelationListPullerBase;
import org.springframework.stereotype.Component;

import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.socialstalker.instagram.relation.InstagramRelationType.FOLLOWING;

@Component
public class InstagramRelationListPuller extends RelationListPullerBase<InstagramID> {
  public InstagramRelationListPuller(InstagramRelationListFactory relationListFactory,
                                     UpdateFactory updateFactory,
                                     RelationFactory relationFactory,
                                     InstagramUserFactory userFactory,
                                     InstagramApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::getFollowers, FOLLOWER);
    registerQuery(api::getFollowing, FOLLOWING);
  }
}
