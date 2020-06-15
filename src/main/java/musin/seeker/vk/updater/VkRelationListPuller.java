package musin.seeker.vk.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.updater.RelationListPuller;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static musin.seeker.vk.relation.VkRelationType.FOLLOWER;
import static musin.seeker.vk.relation.VkRelationType.FRIEND;

@Component
@RequiredArgsConstructor
public class VkRelationListPuller implements RelationListPuller<VkID, VkRelationList> {

  private final VkApi vkApi;
  private final VkUserFactory vkUserFactory;

  private VkRelation createRelation(int id, VkRelationType type) {
    return new VkRelation(vkUserFactory.create(new VkID(id)), type);
  }

  @Override
  public CompletableFuture<VkRelationList> pull(VkID userId) {
    var friends = vkApi.loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FRIEND)));
    var followers = vkApi.loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> createRelation(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }
}
