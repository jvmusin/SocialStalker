package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

public abstract class HashMapRelationList<
    TUser extends User<?>,
    TRelationType,
    TRelation extends Relation<? extends TUser, ? extends TRelationType>,
    TUpdate extends Update<? extends TUser, ? extends TRelationType>>
    implements RelationList<TUser, TRelationType, TRelation, TUpdate> {

  protected final Map<TUser, Set<TRelationType>> userRelations = new HashMap<>();

  protected void initRelations(@NotNull Stream<? extends TRelation> relations) {
    relations.forEach(relation -> apply(createUpdate(relation.getUser(), null, relation.getType())));
  }

  protected void initUpdates(@NotNull List<? extends TUpdate> updates) {
    updates.forEach(this::apply);
  }

  @Override
  public @NotNull Stream<TUser> users() {
    return userRelations.keySet().stream();
  }

  @Override
  public @NotNull Stream<TRelation> relations() {
    return userRelations.entrySet().stream()
        .flatMap(e -> e.getValue().stream().map(t -> createRelation(e.getKey(), t)));
  }

  @Override
  public @NotNull Set<TRelationType> getAllRelationTypes(@NotNull TUser user) {
    return userRelations.getOrDefault(user, emptySet());
  }

  protected void validateUpdate(TUpdate update) {
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

  /**
   * Creates an update
   *
   * @param user a user to create a relation update for
   * @param was  previous relation
   * @param now  new relation
   * @return an update
   */
  protected abstract @NotNull TUpdate createUpdate(@NotNull TUser user, TRelationType was, TRelationType now);

  /**
   * Create a relation
   *
   * @param user a user to create a relation for
   * @param type a type of relation to create
   * @return a relation
   */
  protected abstract @NotNull TRelation createRelation(@NotNull TUser user, TRelationType type);
}
