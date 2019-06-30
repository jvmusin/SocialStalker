package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.RelationList;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static musin.seeker.vkseeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.vkseeker.db.model.RelationType.FRIEND;

@Service
public class VkApiImpl implements VkApi {

    private final VkApiClient vkApi;
    private final UserActor userActor;
    private final Map<Integer, SimpleVkUser> usersCache = new HashMap<>();

    public VkApiImpl(TransportClient transportClient) {
        vkApi = new VkApiClient(transportClient);
        userActor = new MusinUserActor();
    }

    @Override
    @SneakyThrows
    public List<Integer> loadFriends(int userId) {
        return vkApi.friends()
                .get(userActor)
                .userId(userId)
                .lang(EN)
                .execute()
                .getItems();
    }

    @Override
    @SneakyThrows
    public List<Integer> loadFollowers(int userId) {
        return vkApi.users()
                .getFollowers(userActor)
                .count(1000)
                .userId(userId)
                .lang(EN)
                .execute()
                .getItems();
    }

    @Override
    public SimpleVkUser loadUser(int userId) {
        return loadUsers(singletonList(userId)).get(0);
    }

    @Override
    public List<SimpleVkUser> loadUsers(List<Integer> userIds) {
        loadUsersToCache(userIds);
        return userIds.stream()
                .map(usersCache::get)
                .collect(toList());
    }

    @SneakyThrows
    private void loadUsersToCache(List<Integer> userIds) {
        List<String> asString = userIds.stream()
                .filter(id -> !usersCache.containsKey(id))
                .distinct()
                .map(Object::toString)
                .collect(toList());
        if (asString.isEmpty()) return;
        vkApi.users()
                .get(userActor)
                .userIds(asString)
                .lang(EN)
                .execute()
                .forEach(u -> usersCache.put(
                        u.getId(),
                        new SimpleVkUser(u.getId(), u.getFirstName(), u.getLastName()))
                );
    }

    @Override
    public List<RelationChange> buildChangesForNewUser(int userId) {
        final List<Integer> friends = loadFriends(userId);
        final List<Integer> followers = loadFollowers(userId);
        final RelationList list = new RelationList(userId);
        friends.forEach(id -> list.applyChange(RelationChange.builder().curType(FRIEND).target(id).build()));
        followers.forEach(id -> list.applyChange(RelationChange.builder().curType(FOLLOWER).target(id).build()));
        return list.getActiveChanges();
    }
}