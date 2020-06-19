package musin.seeker.relation.list;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

@RequiredArgsConstructor
public abstract class HashMapRelationList<TUser, TRelationType>
    implements RelationList<TUser, TRelationType> {

  protected final Map<TUser, Set<TRelationType>> userRelations = new HashMap<>();

  @Override
  public Stream<TUser> users() {
    return userRelations.keySet().stream();
  }

  @Override
  public Set<TRelationType> getAllRelationTypes(TUser user) {
    return userRelations.getOrDefault(user, emptySet());
  }

  protected void validateUpdate(Update<? extends TUser, ? extends TRelationType> update) {
    if (update.getTarget() == null) throw new IllegalArgumentException("Target is null: " + update);
    if (Objects.equals(update.getWas(), update.getNow()))
      throw new IllegalArgumentException("Was and now types are same: " + update);
  }

  @Override
  public TRelationType getRelationType(TUser user) {
    Set<TRelationType> types = userRelations.getOrDefault(user, emptySet());
    if (types.isEmpty()) return null;
    if (types.size() == 1) return types.iterator().next();
    throw new RuntimeException("More than one relation for user " + user);
  }
}
