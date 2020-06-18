package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public abstract class MultiHashMapRelationList<TUser, TRelationType>
    extends HashMapRelationList<TUser, TRelationType> {

  @Override
  public void apply(@NotNull Update<? extends TUser, ? extends TRelationType> update) {
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
  public @NotNull <TUpdate> Stream<TUpdate> updates(@NotNull RelationList<TUser, ? extends TRelationType> newer,
                                                    @NotNull UpdateFactory<? super TUser, ? super TRelationType, ? extends TUpdate> updateFactory) {
    return concat(users(), newer.users()).distinct().flatMap(user -> {
      var curTypes = getAllRelationTypes(user);
      var newerTypes = newer.getAllRelationTypes(user);
      return concat(
          curTypes.stream().filter(type -> !newerTypes.contains(type)).map(type -> updateFactory.create(user, type, null)),
          newerTypes.stream().filter(type -> !curTypes.contains(type)).map(type -> updateFactory.create(user, null, type))
      );
    });
  }
}
