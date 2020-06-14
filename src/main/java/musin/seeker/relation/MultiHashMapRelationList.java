package musin.seeker.relation;

import musin.seeker.notifier.User;
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
    TUser extends User,
    TRelation extends Relation<? extends TUser, ?>,
    TRelationUpdate extends Update<? extends TUser, ? extends TRelation>>
    extends HashMapRelationList<TUser, TRelation, TRelationUpdate> {

  @Override
  public void apply(@NotNull TRelationUpdate update) {
    if ((update.getWas() == null) == (update.getNow() == null))
      throw new RuntimeException("An update should remove or add a relation, not update: " + update);

    Set<TRelation> relations = userRelations.computeIfAbsent(update.getTarget(), t -> new HashSet<>());

    if (update.getWas() != null && !relations.remove(update.getWas()))
      throw new RuntimeException("The relation is not presented in the list: " + update.getWas());

    if (update.getNow() != null) {
      if (relations.contains(update.getNow()))
        throw new RuntimeException("The relation already exists: " + update.getNow());
      relations.add(update.getNow());
    }

    if (relations.isEmpty()) userRelations.remove(update.getTarget());
  }

  @Override
  public @NotNull Stream<TRelationUpdate> updates(@NotNull RelationList<TUser, TRelation, TRelationUpdate> newer) {
    List<TRelation> thisRelations = relations().collect(toList());
    List<TRelation> newerRelations = newer.relations().collect(toList());
    Collection<TRelation> same = intersection(thisRelations, newerRelations);
    Stream<TRelation> removed = thisRelations.stream().filter(r -> !same.contains(r));
    Stream<TRelation> added = newerRelations.stream().filter(r -> !same.contains(r));
    return concat(
        added.map(r -> createUpdate(r.getUser(), null, r)),
        removed.map(r -> createUpdate(r.getUser(), r, null))
    );
  }
}
