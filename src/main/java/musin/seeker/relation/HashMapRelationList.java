package musin.seeker.relation;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

@RequiredArgsConstructor
public abstract class HashMapRelationList<
    TUser extends User<?>,
    TRelationType,
    TRelation extends Relation<? extends TUser, ? extends TRelationType>,
    TUpdate extends Update<? extends TUser, ? extends TRelationType>>
    implements RelationList<TUser, TRelationType, TRelation, TUpdate> {

  protected final Map<TUser, Set<TRelationType>> userRelations = new HashMap<>();
  protected final UpdateFactory<TUser, TRelationType, TUpdate> updateFactory;
  protected final RelationFactory<?, TUser, TRelationType, TRelation> relationFactory;

  @Override
  public @NotNull Stream<TUser> users() {
    return userRelations.keySet().stream();
  }

  @Override
  public @NotNull Stream<TRelation> relations() {
    return userRelations.entrySet().stream()
        .flatMap(e -> e.getValue().stream().map(t -> relationFactory.create(e.getKey(), t)));
  }

  @Override
  public @NotNull Set<TRelationType> getAllRelationTypes(@NotNull TUser user) {
    return userRelations.getOrDefault(user, emptySet());
  }

  protected void validateUpdate(Update<? extends TUser, ? extends TRelationType> update) {
    if (update.getTarget() == null) throw new IllegalArgumentException("Target is null: " + update);
    if (Objects.equals(update.getWas(), update.getNow()))
      throw new IllegalArgumentException("Was and now types are same: " + update);
  }

  @Override
  public TRelationType getRelationType(@NotNull TUser user) {
    Set<TRelationType> types = userRelations.getOrDefault(user, emptySet());
    if (types.isEmpty()) return null;
    if (types.size() == 1) return types.iterator().next();
    throw new RuntimeException("More than one relation for user " + user);
  }
}
