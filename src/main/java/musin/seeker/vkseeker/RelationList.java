package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.RelationChange;

import java.time.LocalDateTime;
import java.util.*;

import static musin.seeker.vkseeker.RelationType.*;

@AllArgsConstructor
public class RelationList {
    private final int owner;
    private final Set<Integer> friends = new TreeSet<>();
    private final Set<Integer> followers = new TreeSet<>();

    public void applyChange(RelationChange change) {
        Integer id = change.getTarget();

        String prevType = change.getPrevType();
        if (FRIEND.equals(prevType)) friends.remove(id);
        if (FOLLOWER.equals(prevType)) followers.remove(id);

        String curType = change.getCurType();
        if (FRIEND.equals(curType)) friends.add(id);
        if (FOLLOWER.equals(curType)) followers.add(id);
    }

    public void applyChanges(Iterable<RelationChange> changes) {
        changes.forEach(this::applyChange);
    }

    public List<RelationChange> getDifference(RelationList other) {
        Set<Integer> allIds = new TreeSet<>();
        allIds.addAll(friends);
        allIds.addAll(followers);
        allIds.addAll(other.friends);
        allIds.addAll(other.followers);

        LocalDateTime time = LocalDateTime.now();
        List<RelationChange> result = new ArrayList<>();
        for (int id : allIds) {
            String wasType = getType(id);
            String nowType = other.getType(id);
            if (wasType.equals(nowType)) continue;
            result.add(RelationChange.builder()
                    .owner(owner)
                    .target(id)
                    .prevType(wasType)
                    .curType(nowType)
                    .time(time)
                    .build()
            );
        }
        return result;
    }

    private String getType(int id) {
        if (friends.contains(id)) return FRIEND;
        if (followers.contains(id)) return FOLLOWER;
        return NONE;
    }

    public List<RelationChange> getActiveChanges() {
        return new RelationList(owner).getDifference(this);
    }
}