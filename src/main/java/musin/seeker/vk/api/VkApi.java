package musin.seeker.vk.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkApi {
  SimpleVkUser loadUser(int userId);

  CompletableFuture<List<Integer>> loadFriendsAsync(int userId);

  CompletableFuture<List<Integer>> loadFollowersAsync(int userId);
}
