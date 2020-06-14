package musin.seeker.relation;

import musin.seeker.notifier.User;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

public abstract class HashMapRelationList<
    TUser extends User,
    TRelation extends Relation<? extends TUser, ?>,
    TRelationUpdate extends Update<? extends TUser, ? extends TRelation>>
    implements RelationList<TUser, TRelation, TRelationUpdate> {

  protected final Map<TUser, Set<TRelation>> userRelations = new HashMap<>();

  @Override
  public @NotNull Stream<TUser> users() {
    return userRelations.keySet().stream();
  }

  @Override
  public @NotNull Stream<TRelation> relations() {
    return userRelations.values().stream().flatMap(Collection::stream);
  }

//  @Override
//  public @NotNull Set<TRelation> getRelations(@NotNull TUser user) {
//    return userRelations.getOrDefault(user, emptySet());
//  }

  protected static void validateUpdate(Update<? extends User, ? extends Relation<?, ?>> update) {
    if (update.getTarget() == null) throw new IllegalArgumentException("Target is null: " + update);
    if (update.getWas() == null) throw new IllegalArgumentException("Was is null: " + update);
    if (update.getNow() == null) throw new IllegalArgumentException("Now is null: " + update);
    if (!Objects.equals(update.getTarget(), update.getWas().getUser())) throw new IllegalArgumentException("Target and was.user are different: " + update);
    if (!Objects.equals(update.getTarget(), update.getNow().getUser())) throw new IllegalArgumentException("Target and now.user are different: " + update);
    if (Objects.equals(update.getWas().getType(), update.getNow().getType())) throw new IllegalArgumentException("Was.type and now.type are same: " + update);
  }

  @Override
  public TRelation getSingleRelation(@NotNull TUser user) {
    Set<TRelation> relations = userRelations.getOrDefault(user, emptySet());
    if (relations.isEmpty()) return null;
    if (relations.size() == 1) return relations.iterator().next();
    throw new RuntimeException("More than one relation for user " + user);
  }

//  @Override
//  public @NotNull Stream<TRelationUpdate> asUpdates() {
//    return relations().map(r -> createUpdate(r.getUser(), null, r));
//  }

  /**
   * Creates an update
   *
   * @param user a user to create a relation update for
   * @param was  previous relation
   * @param now  new relation
   * @return an update
   */
  protected abstract @NotNull TRelationUpdate createUpdate(@NotNull TUser user, TRelation was, TRelation now);
}
