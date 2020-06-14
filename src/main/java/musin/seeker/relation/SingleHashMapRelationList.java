package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Stream.concat;

public abstract class SingleHashMapRelationList<
    TUser extends User,
    TRelationType,
    TRelation extends Relation<? extends TUser, TRelationType>,
    TRelationUpdate extends RelationUpdate<? extends TUser, ? extends TRelationType>>
    extends HashMapRelationList<TUser, TRelationType, TRelation, TRelationUpdate> {

  @Override
  public void apply(@NotNull TRelationUpdate update) {
    validateUpdate(update);

    if (!Objects.equals(update.getWas(), getRelationType(update.getTarget())))
      throw new RuntimeException("Was and saved types differ for update " + update);

    if (update.getNow() == null) userRelations.remove(update.getTarget());
    else userRelations.put(update.getTarget(), singleton(update.getNow()));
  }

  @Override
  public @NotNull Stream<TRelationUpdate> updates(@NotNull RelationList<TUser, TRelationType, TRelation, TRelationUpdate> newer) {
    return concat(users(), newer.users()).distinct()
        .filter(u -> !Objects.equals(getRelationType(u), newer.getRelationType(u)))
        .map(u -> createUpdate(u, getRelationType(u), newer.getRelationType(u)));
  }
}
