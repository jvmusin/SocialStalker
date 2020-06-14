package musin.seeker.vk.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkApiImpl implements VkApi {

  private final VkApiClient vkApiClient;
  private final UserActor userActor;
  private final AsyncListenableTaskExecutor taskExecutor;
  private final Map<Integer, SimpleVkUser> usersCache = new ConcurrentHashMap<>();

  @Override
  public SimpleVkUser loadUser(int userId) {
    return loadUsers(singleton(userId)).get(0);
  }

  private List<SimpleVkUser> loadUsers(Set<Integer> userIds) {
    loadUsersToCache(userIds);
    return userIds.stream()
        .map(usersCache::get)
        .collect(toList());
  }

  @SneakyThrows
  private void loadUsersToCache(Set<Integer> userIds) {
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
    return taskExecutor.submitListenable(() -> vkApiClient
        .friends()
        .get(userActor)
        .userId(userId)
        .lang(EN)
        .execute()
        .getItems()
    ).completable();
  }

  @Override
  public CompletableFuture<List<Integer>> loadFollowersAsync(int userId) {
    return taskExecutor.submitListenable(() -> vkApiClient
        .users()
        .getFollowers(userActor)
        .count(1000)
        .userId(userId)
        .lang(EN)
        .execute()
        .getItems()
    ).completable();
  }
}
