package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.relation.*;
import musin.seeker.updater.RelationListPuller;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.instagram.relation.InstagramRelationType.FOLLOWER;
import static musin.seeker.instagram.relation.InstagramRelationType.FOLLOWING;

@Component
@Profile("instagram")
@RequiredArgsConstructor
public class InstagramRelationListPuller implements RelationListPuller<InstagramID, InstagramRelationList> {

  private final InstagramApi instagramApi;
  private final InstagramUserFactory instagramUserFactory;

  private InstagramRelation createRelation(InstagramID id, InstagramRelationType type) {
    return new InstagramRelation(instagramUserFactory.create(id), type);
  }

  @Override
  public CompletableFuture<InstagramRelationList> pull(InstagramID userId) {
    var followers = instagramApi.loadFollowers(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)));
    var following = instagramApi.loadFollowing(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWING)));
    return followers.thenCombine(following, Stream::concat).thenApply(InstagramRelationList::new);
  }
}
