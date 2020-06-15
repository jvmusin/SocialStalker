package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public abstract class MultiHashMapRelationList<
    TUser extends User<?>,
    TRelationType,
    TRelation extends Relation<? extends TUser, ? extends TRelationType>,
    TUpdate extends Update<? extends TUser, ? extends TRelationType>>
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
  public @NotNull Stream<TUpdate> updates(@NotNull RelationList<TUser, ? extends TRelationType, ?, ?> newer) {
    return concat(users(), newer.users()).distinct().flatMap(user -> {
      var curTypes = getAllRelationTypes(user);
      var newerTypes = newer.getAllRelationTypes(user);
      return concat(
          curTypes.stream().filter(type -> !newerTypes.contains(type)).map(type -> createUpdate(user, type, null)),
          newerTypes.stream().filter(type -> !curTypes.contains(type)).map(type -> createUpdate(user, null, type))
      );
    });
  }
}
