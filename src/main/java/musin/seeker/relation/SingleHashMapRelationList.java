package musin.seeker.relation;

import musin.seeker.notifier.User;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Stream.concat;

public abstract class SingleHashMapRelationList<
    TUser extends User,
    TRelation extends Relation<? extends TUser, ?>,
    TRelationUpdate extends Update<? extends TUser, ? extends TRelation>>
    extends HashMapRelationList<TUser, TRelation, TRelationUpdate> {

  @Override
  public void apply(@NotNull TRelationUpdate update) {
    if (Objects.equals(update.getWas(), update.getNow()))
      throw new RuntimeException("Was and now types are same in update " + update);

    if (!Objects.equals(update.getWas(), getSingleRelation(update.getTarget())))
      throw new RuntimeException("Was and saved types differ for update " + update);

    if (update.getNow() == null) userRelations.remove(update.getTarget());
    else userRelations.put(update.getTarget(), singleton(update.getNow()));
  }

  @Override
  public @NotNull Stream<TRelationUpdate> updates(@NotNull RelationList<TUser, TRelation, TRelationUpdate> newer) {
    return concat(users(), newer.users()).distinct()
        .filter(u -> !Objects.equals(getSingleRelation(u), newer.getSingleRelation(u)))
        .map(u -> createUpdate(u, getSingleRelation(u), newer.getSingleRelation(u)));
  }
}
