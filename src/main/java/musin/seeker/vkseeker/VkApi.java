package musin.seeker.vkseeker;

import com.vk.api.sdk.objects.users.UserXtrCounters;
import musin.seeker.vkseeker.db.RelationChange;

import java.util.List;

public interface VkApi {
    List<Integer> loadFriends(int userId);
    List<Integer> loadFollowers(int userId);

    SimpleVkUser loadUser(int userId);

    List<SimpleVkUser> loadUsers(List<Integer> userIds);

    List<RelationChange> buildChangesForNewUser(int usedId);
}
