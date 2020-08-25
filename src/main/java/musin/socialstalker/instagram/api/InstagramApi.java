package musin.socialstalker.instagram.api;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.actions.users.UserAction;
import com.github.instagram4j.instagram4j.models.user.Profile;
import com.github.instagram4j.instagram4j.models.user.User;
import com.github.instagram4j.instagram4j.requests.IGGetRequest;
import com.github.instagram4j.instagram4j.requests.IGRequest;
import com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest;
import com.github.instagram4j.instagram4j.requests.users.UsersInfoRequest;
import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedUsersResponse;
import com.github.instagram4j.instagram4j.responses.users.UserResponse;
import lombok.SneakyThrows;
import musin.socialstalker.api.SocialApi;
import musin.socialstalker.util.TimedSemaphore;
import musin.socialstalker.util.TimedSemaphoreFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWERS;
import static com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWING;
import static java.util.stream.Collectors.toList;

@Component
public class InstagramApi implements SocialApi<InstagramID> {

  private final IGClient client;
  private final TimedSemaphore semaphore;
  private final Map<InstagramID, InstagramApiUser> users = new ConcurrentHashMap<>();
  private final AsyncListenableTaskExecutor taskExecutor;

  public InstagramApi(IGClient client, TimedSemaphoreFactory timedSemaphoreFactory, AsyncListenableTaskExecutor taskExecutor) {
    this.client = client;
    this.taskExecutor = taskExecutor;

    // make two queries per 10 seconds, probably it will work (nobody knows the real restrictions)
    this.semaphore = timedSemaphoreFactory.create(2, Duration.ofSeconds(10));
  }

  @SneakyThrows
  private <T extends IGResponse> CompletableFuture<T> makeRequest(IGRequest<T> request) {
    return taskExecutor.submitListenable(() -> semaphore.execute(() -> client.sendRequest(request).join())).completable();
  }

  private InstagramApiUser mapUser(User u) {
    return new InstagramApiUser(new InstagramID(u.getPk()), u.getUsername(), u.getFull_name());
  }

  private InstagramApiUser mapUser(Profile u) {
    return new InstagramApiUser(new InstagramID(u.getPk()), u.getUsername(), u.getFull_name());
  }

  private void saveUser(InstagramApiUser user) {
    users.put(user.getId(), user);
  }

  public InstagramApiUser getUserInfo(InstagramID userId) {
    return users.computeIfAbsent(userId, id ->
        makeRequest(new UsersInfoRequest(id.getValue()))
            .thenApply(UserResponse::getUser)
            .thenApply(this::mapUser)
            .join()
    );
  }

  public CompletableFuture<InstagramApiUser> searchUsername(String username) {
    return client.actions().users().findByUsername(username)
        .thenApply(UserAction::getUser)
        .thenApply(this::mapUser)
        .whenComplete((u, e) -> {
          if (u != null) saveUser(u);
        });
  }

  private List<InstagramID> usersToIds(List<Profile> users) {
    return users.stream()
        .map(this::mapUser)
        .peek(this::saveUser)
        .map(InstagramApiUser::getId)
        .collect(toList());
  }

  private CompletableFuture<List<InstagramID>> load(IGGetRequest<FeedUsersResponse> request) {
    return makeRequest(request)
        .thenApply(FeedUsersResponse::getUsers)
        .thenApply(this::usersToIds);
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> getFollowers(InstagramID userId) {
    return load(new FriendshipsFeedsRequest(userId.getValue(), FOLLOWERS));
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> getFollowing(InstagramID userId) {
    return load(new FriendshipsFeedsRequest(userId.getValue(), FOLLOWING));
  }

  @Override
  public Optional<InstagramID> searchByUsername(String username) {
    return Optional.ofNullable(searchUsername(username).join())
        .map(InstagramApiUser::getId);
  }

  @Override
  public Optional<InstagramID> searchById(InstagramID userId) {
    return Optional.ofNullable(getUserInfo(userId))
        .map(InstagramApiUser::getId);
  }
}
