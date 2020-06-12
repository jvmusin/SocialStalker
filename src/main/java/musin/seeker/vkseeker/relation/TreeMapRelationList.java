package musin.seeker.vkseeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * A {@link RelationList} implementation which uses {@link TreeMap}{@code <TUser, TRelation>} inside
 * and relies on {@code TUser.compareTo(TUser)}.
 * <p>
 * To make things work correctly {@code TUser} and {@code TRelation} should have working {@link Object#equals(Object)} method.
 *
 * @param <TUser>           type of user, should implement {@link Comparable}{@code <TUser>}
 * @param <TRelation>       type of relation
 * @param <TRelationUpdate> type of relation update
 */
public abstract class TreeMapRelationList<TUser extends Comparable<TUser>, TRelation extends Relation<TUser, ?>, TRelationUpdate>
    extends AbstractCollection<TRelation>
    implements RelationList<TUser, TRelation, TRelationUpdate> {

  private final Map<TUser, TRelation> userToRelation = new TreeMap<>();

  @Override
  public boolean add(@NotNull TRelation relation) {
    return relation.getType() == null
        ? userToRelation.remove(relation.getUser()) != null
        : !Objects.equals(relation, userToRelation.put(relation.getUser(), relation));
  }

  @Override
  public TRelation get(@NotNull TUser user) {
    return userToRelation.get(user);
  }

  @NotNull
  @Override
  public Iterator<TRelation> iterator() {
    return new Itr();
  }

  @Override
  public int size() {
    return userToRelation.size();
  }

  @NotNull
  @Override
  public Stream<TUser> users() {
    return userToRelation.keySet().stream();
  }

  @NotNull
  @Override
  public Stream<TRelationUpdate> updates(@NotNull RelationList<TUser, TRelation, TRelationUpdate> newer) {
    return Stream.concat(users(), newer.users())
        .sorted()
        .distinct()
        .filter(u -> !Objects.equals(get(u), newer.get(u)))
        .map(u -> createUpdate(u, get(u), newer.get(u)));
  }

  @NotNull
  @Override
  public Stream<TRelationUpdate> asUpdates() {
    return userToRelation.entrySet().stream()
        .map(e -> createUpdate(e.getKey(), null, e.getValue()));
  }

  protected abstract TRelationUpdate createUpdate(@NotNull TUser user, TRelation was, TRelation now);

  private class Itr implements Iterator<TRelation> {
    private final Iterator<Map.Entry<TUser, TRelation>> iter = userToRelation.entrySet().iterator();

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public TRelation next() {
      return iter.next().getValue();
    }
  }
}
