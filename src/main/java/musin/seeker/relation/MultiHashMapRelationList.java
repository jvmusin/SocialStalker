package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static org.apache.commons.collections4.CollectionUtils.intersection;

public abstract class MultiHashMapRelationList<
    TUser extends User<?>,
    TRelationType,
    TRelation extends Relation<? extends TUser, TRelationType>,
    TUpdate extends Update<? extends TUser, TRelationType>>
    extends HashMapRelationList<TUser, TRelationType, TRelation, TUpdate> {

  @Override
  public void apply(@NotNull TUpdate update) {
    validateUpdate(update);

    if ((update.getWas() == null) == (update.getNow() == null))
      throw new IllegalArgumentException("An update should remove or add a relation, not update: " + update);

    Set<TRelationType> types = userRelations.computeIfAbsent(update.getTarget(), t -> new HashSet<>());

    if (update.getWas() != null && !types.remove(update.getWas()))
      throw new RuntimeException("The relation is not presented in the list: " + update.getWas());

    if (update.getNow() != null) {
      if (types.contains(update.getNow()))
        throw new RuntimeException("The relation already exists: " + update.getNow());
      types.add(update.getNow());
    }

    if (types.isEmpty()) userRelations.remove(update.getTarget());
  }

  @Override
  public @NotNull Stream<TUpdate> updates(@NotNull RelationList<TUser, TRelationType, TRelation, TUpdate> newer) {
    List<TRelation> thisRelations = relations().collect(toList());
    List<TRelation> newerRelations = newer.relations().collect(toList());
    Collection<TRelation> same = intersection(thisRelations, newerRelations);
    Stream<TRelation> removed = thisRelations.stream().filter(r -> !same.contains(r));
    Stream<TRelation> added = newerRelations.stream().filter(r -> !same.contains(r));
    return concat(
        added.map(r -> createUpdate(r.getUser(), null, r.getType())),
        removed.map(r -> createUpdate(r.getUser(), r.getType(), null))
    );
  }
}
