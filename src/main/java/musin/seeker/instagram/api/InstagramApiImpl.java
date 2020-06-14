package musin.seeker.instagram.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserInfoRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstagramApiImpl implements InstagramApi {

  private final Instagram4j instagram;

  @Override
  @SneakyThrows
  //todo add caching
  public InstagramApiUser loadUser(long userId) {
    InstagramSearchUsernameResult res = instagram.sendRequest(new InstagramGetUserInfoRequest(userId));
    InstagramUser user = res.getUser();
    return new InstagramApiUser(userId, user.username);
  }

  @Override
  @SneakyThrows
  public CompletableFuture<List<Long>> loadFollowers(long userId) {
    InstagramGetUserFollowersResult githubFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(userId));
    List<InstagramUserSummary> users = githubFollowers.getUsers();
    List<Long> result = users.stream().map(u -> u.pk).collect(toList());
    return completedFuture(result);
  }
}
