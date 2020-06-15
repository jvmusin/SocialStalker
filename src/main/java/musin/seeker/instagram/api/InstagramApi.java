package musin.seeker.instagram.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InstagramApi {
  InstagramApiUser loadUser(long userId);

  CompletableFuture<List<Long>> loadFollowers(long userId);

  CompletableFuture<List<Long>> loadFollowing(long userId);
}
