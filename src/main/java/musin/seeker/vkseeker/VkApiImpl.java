package musin.seeker.vkseeker;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.db.RelationChange;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.vk.api.sdk.client.Lang.EN;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static musin.seeker.vkseeker.RelationType.*;

@Service
public class VkApiImpl implements VkApi {

    private final VkApiClient vkApi;
    private final UserActor userActor;
    private LocalDateTime lastRequest = LocalDateTime.now();
    private static final int DELAY = 400;

    public VkApiImpl() {
        vkApi = new VkApiClient(new HttpTransportClient());
        userActor = new MusinUserActor();
    }

    @SneakyThrows
    private void sleep() {
        long dist = Duration.between(lastRequest, LocalDateTime.now()).toMillis();
        long wait = DELAY - dist;
        if (wait > 0) Thread.sleep(wait);
        lastRequest = LocalDateTime.now();
    }

    @Override
    @SneakyThrows
    public List<Integer> loadFriends(int userId) {
        sleep();
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
        sleep();
        return vkApi.users()
                .getFollowers(userActor)
                .count(1000)
                .userId(userId)
                .lang(EN)
                .execute()
                .getItems();
    }

    private final Map<Integer, SimpleVkUser> usersCache = new HashMap<>();
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
        userIds = new ArrayList<>(userIds);
        userIds.removeIf(usersCache::containsKey);
        if (userIds.isEmpty()) return;
        sleep();
        List<String> asString = userIds.stream()
                .map(Object::toString)
                .collect(toList());
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
    public List<RelationChange> buildChanges(int userId) {
        final List<Integer> friends = loadFriends(userId);
        final List<Integer> followers = loadFollowers(userId);
        final RelationList list = new RelationList(userId);
        friends.forEach(id -> list.applyChange(RelationChange.builder().curType(FRIEND).target(id).build()));
        followers.forEach(id -> list.applyChange(RelationChange.builder().curType(FOLLOWER).target(id).build()));
        return list.getActiveChanges();
    }
}