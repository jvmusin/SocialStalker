package musin.seeker.instagram.updater;

import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.relation.*;
import musin.seeker.updater.RelationListPullerBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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

  private final InstagramApi api;

  public InstagramRelationListPuller(InstagramRelationListFactory relationListFactory,
                                     InstagramUpdateFactory updateFactory,
                                     InstagramApi api,
                                     InstagramRelationFactory relationFactory) {
    super(relationListFactory, updateFactory, relationFactory);
    this.api = api;
  }

  @Override
  public CompletableFuture<InstagramRelationList> pull(InstagramID userId) {
    return combine(
        load(() -> api.loadFollowers(userId), FOLLOWER),
        load(() -> api.loadFollowing(userId), FOLLOWING)
    );
  }
}
