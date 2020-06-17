package musin.seeker.instagram.api;

import lombok.SneakyThrows;
import musin.seeker.util.AcquireResult;
import musin.seeker.util.TimedSemaphore;
import musin.seeker.util.TimedSemaphoreFactory;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
@Profile("instagram")
public class InstagramApi {

  private final Instagram4j instagram;
  private final TimedSemaphore semaphore;
  private final Map<InstagramID, InstagramApiUser> users = new ConcurrentHashMap<>();
  private final AsyncListenableTaskExecutor taskExecutor;

  public InstagramApi(Instagram4j instagram, TimedSemaphoreFactory timedSemaphoreFactory, AsyncListenableTaskExecutor taskExecutor) {
    this.instagram = instagram;
    this.taskExecutor = taskExecutor;

    // make two queries per 10 seconds, probably it will work (nobody knows the real restrictions)
    this.semaphore = timedSemaphoreFactory.create(2, Duration.ofSeconds(10));
  }

  @SneakyThrows
  private <T> CompletableFuture<T> makeRequest(InstagramRequest<T> request) {
    return taskExecutor.submitListenable(() -> {
      try (AcquireResult ignored = semaphore.acquire()) {
        return instagram.sendRequest(request);
      }
    }).completable();
  }

  private InstagramApiUser mapUser(InstagramUserSummary user) {
    return new InstagramApiUser(new InstagramID(user.pk), user.username, user.full_name);
  }

  private InstagramApiUser mapUser(InstagramUser user) {
    return new InstagramApiUser(new InstagramID(user.pk), user.username, user.full_name);
  }

  private void saveUser(InstagramApiUser user) {
    users.put(user.getId(), user);
  }

  public InstagramApiUser loadUser(InstagramID userId) {
    return users.computeIfAbsent(userId, id -> makeRequest(new InstagramGetUserInfoRequest(id.getValue()))
        .thenApply(InstagramSearchUsernameResult::getUser)
        .thenApply(this::mapUser)
        .join());
  }

  private List<InstagramID> usersToIds(List<InstagramUserSummary> users) {
    return users.stream()
        .map(this::mapUser)
        .peek(this::saveUser)
        .map(InstagramApiUser::getId)
        .collect(toList());
  }

  private CompletableFuture<List<InstagramID>> load(
      Function<Long, InstagramGetRequest<InstagramGetUserFollowersResult>> query,
      InstagramID userId) {
    return makeRequest(query.apply(userId.getValue()))
        .thenApply(InstagramGetUserFollowersResult::getUsers)
        .thenApply(this::usersToIds);
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> loadFollowers(InstagramID userId) {
    return load(InstagramGetUserFollowersRequest::new, userId);
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> loadFollowing(InstagramID userId) {
    return load(InstagramGetUserFollowingRequest::new, userId);
  }
}
