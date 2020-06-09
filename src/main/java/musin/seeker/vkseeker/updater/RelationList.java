package musin.seeker.vkseeker.updater;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.RelationType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class RelationList {
  private final int owner;
  private final Map<RelationType, Set<Integer>> relationTypeToIds;

  public RelationList(int owner) {
    this.owner = owner;
    relationTypeToIds = new EnumMap<>(RelationType.class);
    for (RelationType type : RelationType.values()) relationTypeToIds.put(type, new TreeSet<>());
  }

  public RelationList(int owner, Iterable<RelationChange> changes) {
    this(owner);
    applyChanges(changes);
  }

  private static Stream<Integer> getAllIds(RelationList... lists) {
    return Arrays.stream(lists)
        .flatMap(RelationList::getIds)
        .distinct();
  }

  public void applyChange(RelationChange change) {
    int id = change.getTarget();

    RelationType prevType = getType(id);
    relationTypeToIds.get(prevType).remove(id);

    RelationType curType = change.getCurType();
    relationTypeToIds.get(curType).add(id);
  }

  public void applyChanges(Iterable<RelationChange> changes) {
    changes.forEach(this::applyChange);
  }

  public List<RelationChange> getDifferences(RelationList other) {
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
        .collect(toList());
  }

  private Stream<Integer> getIds() {
    return relationTypeToIds.values().stream()
        .flatMap(Collection::stream);
  }

  private RelationType getType(int id) {
    return relationTypeToIds.entrySet().stream()
        .filter(e -> e.getValue().contains(id))
        .findAny()
        .map(Map.Entry::getKey)
        .orElse(RelationType.NONE);
  }

  public List<RelationChange> getActiveChanges() {
    return new RelationList(owner).getDifferences(this);
  }
}