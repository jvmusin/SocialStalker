package musin.seeker.instagram.api;

import lombok.SneakyThrows;
import musin.seeker.util.AcquireResult;
import musin.seeker.util.TimedSemaphore;
import musin.seeker.util.TimedSemaphoreFactory;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserInfoRequest;
import org.brunocvcunha.instagram4j.requests.InstagramRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

@Component
@Profile("instagram")
public class InstagramApi {

  private final Instagram4j instagram;
  private final TimedSemaphore semaphore;
  private final Map<InstagramID, InstagramApiUser> users = new ConcurrentHashMap<>();

  public InstagramApi(Instagram4j instagram, TimedSemaphoreFactory timedSemaphoreFactory) {
    this.instagram = instagram;

    // make two queries per 10 seconds, probably it will work (nobody knows the real restrictions)
    this.semaphore = timedSemaphoreFactory.create(2, Duration.ofSeconds(10));
  }

  @SneakyThrows
  private <T> T makeRequest(InstagramRequest<T> request) {
    try (AcquireResult ignored = semaphore.acquire()) {
      return instagram.sendRequest(request);
    }
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
    return users.computeIfAbsent(userId, id -> {
      InstagramSearchUsernameResult res = makeRequest(new InstagramGetUserInfoRequest(id.getValue()));
      return mapUser(res.getUser());
    });
  }

  private List<InstagramID> followersToIds(List<InstagramUserSummary> followers) {
    return followers.stream()
        .map(this::mapUser)
        .peek(this::saveUser)
        .map(InstagramApiUser::getId)
        .collect(toList());
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> loadFollowers(InstagramID userId) {
    InstagramGetUserFollowersResult followers = makeRequest(new InstagramGetUserFollowersRequest(userId.getValue()));
    return completedFuture(followersToIds(followers.getUsers()));
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> loadFollowing(InstagramID userId) {
    InstagramGetUserFollowersResult following = makeRequest(new InstagramGetUserFollowingRequest(userId.getValue()));
    return completedFuture(followersToIds(following.getUsers()));
  }
}
