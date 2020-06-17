package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.updater.RelationListPuller;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.relation.VkRelationFactory;
import musin.seeker.vk.relation.VkRelationList;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.vk.relation.VkRelationType.FOLLOWER;
import static musin.seeker.vk.relation.VkRelationType.FRIEND;

@Component
@RequiredArgsConstructor
public class VkRelationListPuller implements RelationListPuller<VkID, VkRelationList> {

  private final VkApi api;
  private final VkRelationFactory relationFactory;

  @Override
  public CompletableFuture<VkRelationList> pull(VkID userId) {
    var friends = api.loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> relationFactory.create(id, FRIEND)));
    var followers = api.loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> relationFactory.create(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }
}
