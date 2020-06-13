package musin.seeker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.db.VkRelationType;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.vk.db.VkRelationType.FOLLOWER;
import static musin.seeker.vk.db.VkRelationType.FRIEND;

@Component
@RequiredArgsConstructor
public class VkRelationListPuller {

  private final VkApi vkApi;
  private final VkUserFactory vkUserFactory;

  private VkRelation createRelation(int id, VkRelationType type) {
    return new VkRelation(vkUserFactory.create(id), type);
  }

  public CompletableFuture<VkRelationList> pull(int userId) {
    var friends = vkApi.loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FRIEND)));
    var followers = vkApi.loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }
}
