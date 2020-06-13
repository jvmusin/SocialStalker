package musin.seeker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationType;
import musin.seeker.vk.api.VkApi;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.db.model.RelationType.FRIEND;

@Component
@RequiredArgsConstructor
public class VkRelationListFactory {

  private final VkApi vkApi;
  private final VkUserFactory vkUserFactory;

  private VkRelation createRelation(int id, RelationType type) {
    return new VkRelation(vkUserFactory.create(id), type);
  }

  public CompletableFuture<VkRelationList> queryFromVk(int userId) {
    var friends = vkApi.loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FRIEND)));
    var followers = vkApi.loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }

  public VkRelationList create(Stream<VkRelation> relations) {
    return new VkRelationList(relations);
  }
}
