package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.db.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramRelation;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramUserFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static musin.seeker.instagram.db.InstagramRelationType.FOLLOWER;

@Component
@RequiredArgsConstructor
public class InstagramRelationListPuller {

  private final InstagramApi instagramApi;
  private final InstagramUserFactory instagramUserFactory;

  private InstagramRelation createRelation(long userId, InstagramRelationType type) {
    return new InstagramRelation(instagramUserFactory.create(userId), type);
  }

  public CompletableFuture<InstagramRelationList> pull(long userId) {
    return instagramApi.loadFollowers(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)))
        .thenApply(r -> r)
        .thenApply(InstagramRelationList::new);
  }
}
