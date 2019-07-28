package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.model.RelationChange;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static musin.seeker.vkseeker.db.model.RelationType.*;

@AllArgsConstructor
public class RelationList {
    private final int owner;
    private final Map<String, Set<Integer>> relationTypeToId;

    public RelationList(int owner) {
        this.owner = owner;
        relationTypeToId = new HashMap<>();
        relationTypeToId.put(FRIEND, new TreeSet<>());
        relationTypeToId.put(FOLLOWER, new TreeSet<>());
    }

    public void applyChange(RelationChange change) {
        int id = change.getTarget();

        String prevType = change.getPrevType();
        relationTypeToId.get(prevType).remove(id);

        String curType = change.getCurType();
        relationTypeToId.get(curType).add(id);
    }

    public void applyChanges(Iterable<RelationChange> changes) {
        changes.forEach(this::applyChange);
    }

    public List<RelationChange> getDifference(RelationList other) {
        LocalDateTime time = LocalDateTime.now();
        return getAllIds(this, other)
                .map(id -> RelationChange.builder()
                        .owner(owner)
                        .target(id)
                        .prevType(getType(id))
                        .curType(other.getType(id))
                        .time(time)
                        .build())
                .filter(r -> !r.getPrevType().equals(r.getCurType()))
                .collect(Collectors.toList());
    }

    private static Stream<Integer> getAllIds(RelationList... lists) {
        return Arrays.stream(lists)
                .flatMap(RelationList::getIds);
    }

    private Stream<Integer> getIds() {
        return relationTypeToId.values().stream()
                .flatMap(Collection::stream);
    }

    private String getType(int id) {
        for (Map.Entry<String, Set<Integer>> e : relationTypeToId.entrySet())
            if (e.getValue().contains(id))
                return e.getKey();
        return NONE;
    }

    public List<RelationChange> getActiveChanges() {
        return new RelationList(owner).getDifference(this);
    }
}