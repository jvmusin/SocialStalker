package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
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

  private InstagramRelation createRelation(long userId, InstagramRelationType type) {
    return new InstagramRelation(instagramUserFactory.create(new InstagramID(userId)), type);
  }

  @Override
  public CompletableFuture<InstagramRelationList> pull(InstagramID userId) {
    long uid = userId.getValue();
    var followers = instagramApi.loadFollowers(uid).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)));
    var following = instagramApi.loadFollowing(uid).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWING)));
    return followers.thenCombine(following, Stream::concat).thenApply(InstagramRelationList::new);
  }
}
