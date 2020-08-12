package musin.stalker.instagram.updater;

import musin.stalker.instagram.api.InstagramApi;
import musin.stalker.instagram.api.InstagramID;
import musin.stalker.instagram.relation.InstagramRelationListFactory;
import musin.stalker.instagram.relation.InstagramUserFactory;
import musin.stalker.relation.RelationFactory;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.updater.RelationListPullerBase;
import org.springframework.stereotype.Component;

import static musin.stalker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.stalker.instagram.relation.InstagramRelationType.FOLLOWING;

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
