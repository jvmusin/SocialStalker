package musin.seeker.vkseeker.vk.api;

import musin.seeker.vkseeker.vk.relation.VkRelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkApi {
  List<Integer> loadFriends(int userId);
  List<Integer> loadFollowers(int userId);
  SimpleVkUser loadUser(int userId);
  List<SimpleVkUser> loadUsers(List<Integer> userIds);

  CompletableFuture<List<Integer>> loadFriendsAsync(int userId);
  CompletableFuture<List<Integer>> loadFollowersAsync(int userId);
  CompletableFuture<SimpleVkUser> loadUserAsync(int userId);
  CompletableFuture<List<SimpleVkUser>> loadUsersAsync(List<Integer> userIds);
  CompletableFuture<VkRelationList> loadRelationsAsync(int userId);
}
