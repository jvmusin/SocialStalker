package musin.seeker.vk.api;

import lombok.RequiredArgsConstructor;
import musin.seeker.vk.relation.VkRelationFactory;
import musin.seeker.vk.relation.VkRelationList;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.db.model.RelationType.FRIEND;

@Component
@RequiredArgsConstructor
public class VkRelationListFactory {

  private final VkApi vkApi;
  private final VkRelationFactory vkRelationFactory;

  public CompletableFuture<VkRelationList> create(int userId) {
    var friends = vkApi.loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> vkRelationFactory.create(id, FRIEND)));
    var followers = vkApi.loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> vkRelationFactory.create(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }
}
