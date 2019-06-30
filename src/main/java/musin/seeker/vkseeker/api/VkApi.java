package musin.seeker.vkseeker.api;

import musin.seeker.vkseeker.db.model.RelationChange;

import java.util.List;

public interface VkApi {
    List<Integer> loadFriends(int userId);
    List<Integer> loadFollowers(int userId);

    SimpleVkUser loadUser(int userId);

    List<SimpleVkUser> loadUsers(List<Integer> userIds);

    List<RelationChange> buildChangesForNewUser(int usedId);
}
