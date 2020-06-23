package musin.socialstalker.relation.list;

import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public abstract class MultiHashMapRelationList<TRelationType> extends HashMapRelationList<TRelationType> {

  @Override
  public void apply(Update<? extends TRelationType> update) {
    validateUpdate(update);

    if ((update.getWas() == null) == (update.getNow() == null))
      throw new IllegalArgumentException("An update should remove or add a relation, not update: " + update);

    Set<TRelationType> types = userRelations.computeIfAbsent(update.getSuspected(), t -> new HashSet<>());

    if (update.getWas() != null && !types.remove(update.getWas()))
      throw new RuntimeException("The relation is not presented in the list: " + update.getWas());

    if (update.getNow() != null) {
      if (types.contains(update.getNow()))
        throw new RuntimeException("The relation already exists: " + update.getNow());
      types.add(update.getNow());
    }

    if (types.isEmpty()) userRelations.remove(update.getSuspected());
  }

  @Override
  public Stream<? extends Update<? extends TRelationType>> updates(
      RelationList<? extends TRelationType> newer,
      UpdateFactory<TRelationType> updateFactory) {
    return concat(users(), newer.users()).distinct().flatMap(user -> {
      var curTypes = getAllRelationTypes(user);
      var newerTypes = newer.getAllRelationTypes(user);
      return concat(
          curTypes.stream().filter(type -> !newerTypes.contains(type)).map(type -> updateFactory.removing(user, type)),
          newerTypes.stream().filter(type -> !curTypes.contains(type)).map(type -> updateFactory.creating(user, type))
      );
    });
  }
}
