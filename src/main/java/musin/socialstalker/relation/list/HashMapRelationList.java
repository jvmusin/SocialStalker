package musin.socialstalker.relation.list;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

@RequiredArgsConstructor
public abstract class HashMapRelationList<TRelationType> implements RelationList<TRelationType> {

  protected final Map<User<?>, Set<TRelationType>> userRelations = new HashMap<>();

  @Override
  public Stream<User<?>> users() {
    return userRelations.keySet().stream();
  }

  @Override
  public Set<TRelationType> getAllRelationTypes(User<?> user) {
    return userRelations.getOrDefault(user, emptySet());
  }

  protected void validateUpdate(Update<? extends TRelationType> update) {
    if (update.getSuspected() == null) throw new IllegalArgumentException("Suspected is null: " + update);
    if (Objects.equals(update.getWas(), update.getNow()))
      throw new IllegalArgumentException("Was and now types are same: " + update);
  }

  @Override
  public TRelationType getRelationType(User<?> user) {
    Set<TRelationType> types = userRelations.getOrDefault(user, emptySet());
    if (types.isEmpty()) return null;
    if (types.size() == 1) return types.iterator().next();
    throw new RuntimeException("More than one relation for user " + user);
  }
}
