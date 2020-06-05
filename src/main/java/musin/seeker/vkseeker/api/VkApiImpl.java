package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.RelationList;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;
import static musin.seeker.vkseeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.vkseeker.db.model.RelationType.FRIEND;

@Service
public class VkApiImpl implements VkApi {

  private final VkApiClient vkApi;
  private final UserActor userActor;
  private final Map<Integer, SimpleVkUser> usersCache = new ConcurrentHashMap<>();
  private final Executor executor;

  public VkApiImpl(TransportClient transportClient, @Qualifier("MyTaskExecutor") TaskExecutor executor) {
    vkApi = new VkApiClient(transportClient);
    userActor = new MusinUserActor();
    this.executor = executor;
  }

  @Override
  @SneakyThrows
  public List<Integer> loadFriends(int userId) {
    return vkApi.friends()
        .get(userActor)
        .userId(userId)
        .lang(EN)
        .execute()
        .getItems();
  }

  @SneakyThrows
  @Override
  public List<Integer> loadFollowers(int userId) {
    return vkApi.users()
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
    vkApi.users()
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
  public RelationList loadRelations(int userId) {
    return loadRelationsAsync(userId).join();
  }

  @Override
  public CompletableFuture<List<Integer>> loadFriendsAsync(int userId) {
    return supplyAsync(() -> loadFriends(userId), executor);
  }

  @Override
  public CompletableFuture<List<Integer>> loadFollowersAsync(int userId) {
    return supplyAsync(() -> loadFollowers(userId), executor);
  }

  @Override
  public CompletableFuture<SimpleVkUser> loadUserAsync(int userId) {
    return supplyAsync(() -> loadUser(userId), executor);
  }

  @Override
  public CompletableFuture<List<SimpleVkUser>> loadUsersAsync(List<Integer> userIds) {
    return supplyAsync(() -> loadUsers(userIds), executor);
  }

  @Override
  public CompletableFuture<RelationList> loadRelationsAsync(int userId) {
    CompletableFuture<List<Integer>> friends = loadFriendsAsync(userId);
    CompletableFuture<List<Integer>> followers = loadFollowersAsync(userId);
    return allOf(friends, followers).thenApply(v -> {
      RelationList list = new RelationList(userId);
      friends.join().forEach(id -> list.applyChange(RelationChange.builder().curType(FRIEND).target(id).build()));
      followers.join().forEach(id -> list.applyChange(RelationChange.builder().curType(FOLLOWER).target(id).build()));
      return list;
    });
  }
}
