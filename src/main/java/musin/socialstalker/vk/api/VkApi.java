package musin.socialstalker.vk.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import musin.socialstalker.api.SocialApi;
import org.apache.logging.log4j.Level;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class VkApi implements SocialApi<VkID> {

  private final VkApiClient vkApiClient;
  private final UserActor userActor;
  private final AsyncListenableTaskExecutor taskExecutor;
  private final Map<String, VkApiUser> usersCache = new ConcurrentHashMap<>();

  private void saveUser(VkApiUser user) {
    usersCache.put(user.getId().toString(), user);
    if (user.getNickname() != null)
      usersCache.put(user.getNickname(), user);
  }

  private VkApiUser mapUser(UserXtrCounters user) {
    return new VkApiUser(
        new VkID(user.getId()),
        user.getScreenName(),
        user.getFirstName(),
        user.getLastName()
    );
  }

  public Optional<VkApiUser> getUser(VkID userId) {
    return getUser(userId.toString());
  }

  public Optional<VkApiUser> getUser(String nicknameOrId) {
    if (usersCache.containsKey(nicknameOrId)) return of(usersCache.get(nicknameOrId));
    try {
      return of(getUsers(singletonList(nicknameOrId)).get(0));
    } catch (Exception e) {
      if (!e.getMessage().contains("Invalid user id"))
        log.warn("Vk api thrown an exception", e);
      return empty();
    }
  }

  @SneakyThrows
  private List<VkApiUser> getUsers(List<String> nicknamesOrIds) {
    List<String> unknown = nicknamesOrIds.stream()
        .filter(id -> !usersCache.containsKey(id))
        .collect(toList());

    if (!unknown.isEmpty()) {
      List<UserXtrCounters> users = vkApiClient
          .users()
          .get(userActor)
          .userIds(unknown)
          .fields(Fields.SCREEN_NAME)
          .lang(EN)
          .execute();
      users.forEach(user -> saveUser(mapUser(user)));
    }

    return nicknamesOrIds.stream().map(usersCache::get).collect(toList());
  }

  @SneakyThrows
  private List<VkID> getFriends(VkID userId) {
    List<Integer> ids = vkApiClient
        .friends()
        .get(userActor)
        .userId(userId.getValue())
        .lang(EN)
        .execute()
        .getItems();
    return ids.stream().map(VkID::new).collect(toList());
  }

  @SneakyThrows
  private List<VkID> getFollowers(VkID userId) {
    List<Integer> ids = vkApiClient
        .users()
        .getFollowers(userActor)
        .count(1000)
        .userId(userId.getValue())
        .lang(EN)
        .execute()
        .getItems();
    return ids.stream().map(VkID::new).collect(toList());
  }

  public CompletableFuture<List<VkID>> getFriendsAsync(VkID userId) {
    return taskExecutor.submitListenable(() -> getFriends(userId)).completable();
  }

  public CompletableFuture<List<VkID>> getFollowersAsync(VkID userId) {
    return taskExecutor.submitListenable(() -> getFollowers(userId)).completable();
  }

  @Override
  public Optional<VkID> searchByUsername(String username) {
    try {
      return getUser(username).map(VkApiUser::getId);
    } catch (Exception e) {
      log.warn("Vk api thrown an exception when trying to find a user by username " + username, e);
      return empty();
    }
  }

  @Override
  public Optional<VkID> searchById(VkID userId) {
    // actually same as searchByUsername
    return searchByUsername(userId.toString());
  }
}
