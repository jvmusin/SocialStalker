package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.RelationType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static musin.seeker.vkseeker.db.model.RelationType.*;

@AllArgsConstructor
public class RelationList {
    private final int owner;
    private final Map<RelationType, Set<Integer>> relationTypeToId;

    public RelationList(int owner) {
        this.owner = owner;
        relationTypeToId = new EnumMap<>(RelationType.class);
        for (RelationType type : values()) relationTypeToId.put(type, new TreeSet<>());
    }

    public void applyChange(RelationChange change) {
        int id = change.getTarget();

        RelationType prevType = getType(id);
        relationTypeToId.get(prevType).remove(id);

        RelationType curType = change.getCurType();
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
                .filter(r -> r.getPrevType() != r.getCurType())
                .collect(Collectors.toList());
    }

    private static Stream<Integer> getAllIds(RelationList... lists) {
        return Arrays.stream(lists)
                .flatMap(RelationList::getIds)
                .distinct();
    }

    private Stream<Integer> getIds() {
        return relationTypeToId.values().stream()
                .flatMap(Collection::stream);
    }

    private RelationType getType(int id) {
        for (Map.Entry<RelationType, Set<Integer>> e : relationTypeToId.entrySet())
            if (e.getValue().contains(id))
                return e.getKey();
        return NONE;
    }

    public List<RelationChange> getActiveChanges() {
        return new RelationList(owner).getDifference(this);
    }
}