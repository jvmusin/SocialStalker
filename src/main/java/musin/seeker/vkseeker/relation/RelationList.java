package musin.seeker.vkseeker.relation;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * This class represents all relations between some root user and other users.
 *
 * @param <TUser>           type of users
 * @param <TRelation>       type of relations between users
 * @param <TRelationUpdate> type of relation updates
 */
public interface RelationList<TUser, TRelation extends Relation<TUser, ?>, TRelationUpdate>
    extends Collection<TRelation> {

  /**
   * Returns all users in this list.
   *
   * @return a stream of all users in this list
   */
  Stream<TUser> users();

  /**
   * If a given {@code relation.type} is {@code null}, then removes a relation,
   * associated with a user taken from {@code relation.user}.
   * <p>
   * Otherwise adds or overrides (if already exists) a relation,
   * associated with a user taken from {@code relation.user}.
   *
   * @param relation a relation to add or remove
   * @return {@code true} if this list changed as a result of the call
   */
  @Override
  boolean add(TRelation relation);

  /**
   * Returns a relation associated with a given {@code user}.
   *
   * @param user a user to get a relation for
   * @return a relation associated with a given {@code user}
   * or {@code null} if a user is not presented in this list
   */
  TRelation get(TUser user);

  /**
   * Returns all updates between this list and the given {@code newer} list.
   * <p>
   * An update means any of these:
   * <ul>
   * <li>The user is unknown in this list, but is known in a {@code newer} list, or
   * <li>The user is known in this list, but is unknown in a {@code newer} list, or
   * <li>The user is known in both lists, but the relation types differ.
   * </ul>
   *
   * @param newer a newer relation list to check for updates
   * @return a stream of all updates between these two lists
   */
  Stream<TRelationUpdate> updates(RelationList<TUser, TRelation, TRelationUpdate> newer);

  /**
   * Returns updates such that applying them on an empty list produces a list which is equal to this one.
   *
   * @return a stream of updates to build a list which is equal to this one
   */
  Stream<TRelationUpdate> asUpdates();
}
