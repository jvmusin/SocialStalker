package musin.seeker.relation.list;

import musin.seeker.relation.Update;
import musin.seeker.relation.UpdateFactory;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Stream.concat;

public abstract class SingleHashMapRelationList<TUser, TRelationType>
    extends HashMapRelationList<TUser, TRelationType> {

  @Override
  public void apply(Update<? extends TUser, ? extends TRelationType> update) {
    validateUpdate(update);

    if (!Objects.equals(update.getWas(), getRelationType(update.getTarget())))
      throw new RuntimeException("Was and saved types differ for update " + update);

    if (update.getNow() == null) userRelations.remove(update.getTarget());
    else userRelations.put(update.getTarget(), singleton(update.getNow()));
  }

  @Override
  public <TUpdate> Stream<TUpdate> updates(
      RelationList<TUser, ? extends TRelationType> newer,
      UpdateFactory<? super TUser, ? super TRelationType, ? extends TUpdate> updateFactory) {
    return concat(users(), newer.users()).distinct()
        .filter(u -> !Objects.equals(getRelationType(u), newer.getRelationType(u)))
        .map(u -> updateFactory.updating(u, getRelationType(u), newer.getRelationType(u)));
  }
}
