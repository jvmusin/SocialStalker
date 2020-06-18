package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Stream;

public interface RelationList<TUser, TRelationType> {

  /**
   * @return all users in this list
   */
  @NotNull Stream<TUser> users();

  /**
   * @param user a user to get relations for
   * @return all relation types, associated with a given user
   */
  @NotNull Set<TRelationType> getAllRelationTypes(@NotNull TUser user);

  /**
   * Returns a single relation for a given user.
   * <p>If there is a single relation, associated with a given user, returns it.
   * <p>If there are no relations, associated with a given user, returns null.
   * <p>If there are more than one relation, associated with a given user, throws an exception.
   *
   * @param user a user to get a relation for
   * @return a single relation, associated with a given user, or null
   */
  TRelationType getRelationType(@NotNull TUser user);

  /**
   * @param update an update to apply
   */
  void apply(@NotNull Update<? extends TUser, ? extends TRelationType> update);

  /**
   * @param newer         a newer list to build updates
   * @param updateFactory a factory to create updates with
   * @return updates between this and newer list
   */
  @NotNull <TUpdate> Stream<TUpdate> updates(@NotNull RelationList<TUser, ? extends TRelationType> newer,
                                             @NotNull UpdateFactory<? super TUser, ? super TRelationType, ? extends TUpdate> updateFactory);
}
