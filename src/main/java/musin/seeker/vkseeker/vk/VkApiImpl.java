package musin.seeker.vkseeker.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.vk.relation.VkRelation;
import musin.seeker.vkseeker.vk.relation.VkRelationList;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static musin.seeker.vkseeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.vkseeker.db.model.RelationType.FRIEND;

@Service
@AllArgsConstructor
public class VkApiImpl implements VkApi {

  private final VkApiClient vkApiClient;
  private final UserActor userActor;
  private final AsyncListenableTaskExecutor taskExecutor;
  private final Map<Integer, SimpleVkUser> usersCache = new ConcurrentHashMap<>();

  @Override
  @SneakyThrows
  public List<Integer> loadFriends(int userId) {
    return vkApiClient.friends()
        .get(userActor)
        .userId(userId)
        .lang(EN)
        .execute()
        .getItems();
  }

  @SneakyThrows
  @Override
  public List<Integer> loadFollowers(int userId) {
    return vkApiClient.users()
        .getFollowers(userActor)
        .count(1000)
        .userId(userId)
        .lang(EN)
        .execute()
        .getItems();
  }

  @Override
  public SimpleVkUser loadUser(int userId) {
    return loadUsers(singletonList(userId)).get(0);
  }

  @Override
  public List<SimpleVkUser> loadUsers(List<Integer> userIds) {
    loadUsersToCache(userIds);
    return userIds.stream()
        .map(usersCache::get)
        .collect(toList());
  }

  @SneakyThrows
  private void loadUsersToCache(List<Integer> userIds) {
    List<String> asString = userIds.stream()
        .filter(id -> !usersCache.containsKey(id))
        .distinct()
        .map(Object::toString)
        .collect(toList());
    if (asString.isEmpty()) return;
    vkApiClient.users()
        .get(userActor)
        .userIds(asString)
        .lang(EN)
        .execute()
        .forEach(u -> usersCache.put(
            u.getId(),
            new SimpleVkUser(u.getId(), u.getFirstName(), u.getLastName()))
        );
  }

  @Override
  public CompletableFuture<List<Integer>> loadFriendsAsync(int userId) {
    return taskExecutor.submitListenable(() -> loadFriends(userId)).completable();
  }

  @Override
  public CompletableFuture<List<Integer>> loadFollowersAsync(int userId) {
    return taskExecutor.submitListenable(() -> loadFollowers(userId)).completable();
  }

  @Override
  public CompletableFuture<SimpleVkUser> loadUserAsync(int userId) {
    return taskExecutor.submitListenable(() -> loadUser(userId)).completable();
  }

  @Override
  public CompletableFuture<List<SimpleVkUser>> loadUsersAsync(List<Integer> userIds) {
    return taskExecutor.submitListenable(() -> loadUsers(userIds)).completable();
  }

  @Override
  public CompletableFuture<VkRelationList> loadRelationsAsync(int userId) {
    var friends = loadFriendsAsync(userId).thenApply(s -> s.stream().map(id -> new VkRelation(id, FRIEND)));
    var followers = loadFollowersAsync(userId).thenApply(s -> s.stream().map(id -> new VkRelation(id, FOLLOWER)));
    return friends.thenCombine(followers, Stream::concat).thenApply(VkRelationList::new);
  }
}
