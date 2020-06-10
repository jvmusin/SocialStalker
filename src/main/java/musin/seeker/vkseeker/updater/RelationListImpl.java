package musin.seeker.vkseeker.updater;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Stream;

public abstract class RelationListImpl<TUser, TRelation extends Relation<TUser, ?>, TRelationUpdate>
    extends AbstractCollection<TRelation>
    implements RelationList<TUser, TRelation, TRelationUpdate> {

  private final Map<TUser, TRelation> userToRelation = new HashMap<>();

  @Override
  public boolean add(TRelation relation) {
    return relation.getType() == null
        ? userToRelation.remove(relation.getUser()) != null
        : !Objects.equals(relation, userToRelation.put(relation.getUser(), relation));
  }

  @Override
  public TRelation get(TUser user) {
    return userToRelation.get(user);
  }

  @Override
  @Nonnull
  public Iterator<TRelation> iterator() {
    return new Itr();
  }

  @Override
  public int size() {
    return userToRelation.size();
  }

  @Override
  public Stream<TUser> users() {
    return userToRelation.keySet().stream();
  }

  @Override
  public Stream<TRelationUpdate> updates(RelationList<TUser, TRelation, TRelationUpdate> newer) {
    return Stream.concat(users(), newer.users())
        .filter(u -> !Objects.equals(get(u), newer.get(u)))
        .map(u -> createUpdate(u, get(u), newer.get(u)));
  }

  protected abstract TRelationUpdate createUpdate(TUser user, TRelation was, TRelation now);

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
