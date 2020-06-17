package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.relation.InstagramRelationFactory;
import musin.seeker.instagram.relation.InstagramRelationList;
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

  private final InstagramApi api;
  private final InstagramRelationFactory relationFactory;

  @Override
  public CompletableFuture<InstagramRelationList> pull(InstagramID userId) {
    var followers = api.loadFollowers(userId).thenApply(s -> s.stream().map(id -> relationFactory.create(id, FOLLOWER)));
    var following = api.loadFollowing(userId).thenApply(s -> s.stream().map(id -> relationFactory.create(id, FOLLOWING)));
    return followers.thenCombine(following, Stream::concat).thenApply(InstagramRelationList::new);
  }
}
