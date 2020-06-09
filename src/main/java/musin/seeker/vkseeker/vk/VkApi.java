package musin.seeker.vkseeker.vk;

import musin.seeker.vkseeker.core.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VkApi {
  List<Integer> loadFriends(int userId);
  List<Integer> loadFollowers(int userId);
  SimpleVkUser loadUser(int userId);
  List<SimpleVkUser> loadUsers(List<Integer> userIds);
  RelationList loadRelations(int userId);

  CompletableFuture<List<Integer>> loadFriendsAsync(int userId);
  CompletableFuture<List<Integer>> loadFollowersAsync(int userId);
  CompletableFuture<SimpleVkUser> loadUserAsync(int userId);
  CompletableFuture<List<SimpleVkUser>> loadUsersAsync(List<Integer> userIds);
  CompletableFuture<RelationList> loadRelationsAsync(int userId);
}
