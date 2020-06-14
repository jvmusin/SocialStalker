package musin.seeker.relation.v2;

import musin.seeker.notifier.User;
import musin.seeker.relation.Relation;
import musin.seeker.relation.Update;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
