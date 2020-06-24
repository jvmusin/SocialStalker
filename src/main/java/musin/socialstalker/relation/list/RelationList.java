package musin.socialstalker.relation.list;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;

import java.util.Set;
import java.util.stream.Stream;

public interface RelationList {

  /**
   * @return all users in this list
   */
  Stream<User> users();

  /**
   * @param user a user to get relations for
   * @return all relation types, associated with a given user
   */
  Set<RelationType> getAllRelationTypes(User user);

  /**
   * Returns a single relation for a given user.
   * <p>If there is a single relation, associated with a given user, returns it.
   * <p>If there are no relations, associated with a given user, returns null.
   * <p>If there are more than one relation, associated with a given user, throws an exception.
   *
   * @param user a user to get a relation for
   * @return a single relation, associated with a given user, or null
   */
  RelationType getRelationType(User user);

  /**
   * @param update an update to apply
   */
  void apply(Update update);

  /**
   * @param newer         a newer list to build updates
   * @param updateFactory a factory to create updates with
   * @return updates between this and newer list
   */
  Stream<Update> updates(RelationList newer, UpdateFactory updateFactory);

  default Stream<Update> asUpdates(UpdateFactory updateFactory) {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> updateFactory.creating(u, t)));
  }
}
