package musin.seeker.instagram.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserInfoRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

@Component
@Profile("instagram")
@RequiredArgsConstructor
public class InstagramApi {

  private final Instagram4j instagram;
  private final Map<InstagramID, InstagramApiUser> users = new ConcurrentHashMap<>();

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
    //noinspection Convert2Lambda
    return users.computeIfAbsent(userId, new Function<>() {
      @Override
      @SneakyThrows
      public InstagramApiUser apply(InstagramID id) {
        InstagramSearchUsernameResult res = instagram.sendRequest(new InstagramGetUserInfoRequest(id.getValue()));
        return mapUser(res.getUser());
      }
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
    InstagramGetUserFollowersResult followers = instagram.sendRequest(new InstagramGetUserFollowersRequest(userId.getValue()));
    return completedFuture(followersToIds(followers.getUsers()));
  }

  @SneakyThrows
  public CompletableFuture<List<InstagramID>> loadFollowing(InstagramID userId) {
    InstagramGetUserFollowersResult following = instagram.sendRequest(new InstagramGetUserFollowingRequest(userId.getValue()));
    return completedFuture(followersToIds(following.getUsers()));
  }
}
