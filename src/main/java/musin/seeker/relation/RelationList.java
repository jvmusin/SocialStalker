package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface RelationList<TUser, TRelation, TRelationUpdate> {

  /**
   * @return all users in this list
   */
  @NotNull Stream<TUser> users();

  /**
   * @return all relations in this list
   */
  @NotNull Stream<TRelation> relations();

//  /**
//   * @param user a user to get all relations for
//   * @return a stream of all relations associated with a given user
//   */
//  @NotNull Set<TRelation> getRelations(@NotNull TUser user);

  /**
   * Returns a single relation for a given user.
   * <p>If there is a single relation, associated with a given user, returns it.
   * <p>If there are no relations, associated with a given user, returns null.
   * <p>If there are more than one relation, associated with a given user, throws an exception.
   *
   * @param user a user to get a relation for
   * @return a single relation, associated with a given user, or null
   */
  TRelation getSingleRelation(@NotNull TUser user);

  /**
   * @param update an update to apply
   */
  void apply(@NotNull TRelationUpdate update);

  /**
   * @param newer a newer list to build updates
   * @return updates between this and newer list
   */
  @NotNull Stream<TRelationUpdate> updates(@NotNull RelationList<TUser, TRelation, TRelationUpdate> newer);

//  /**
//   * @return all relations in this list as a stream of updates
//   */
//  @NotNull Stream<TRelationUpdate> asUpdates();
}
