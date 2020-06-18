package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Stream.concat;

public abstract class SingleHashMapRelationList<TUser, TRelationType>
    extends HashMapRelationList<TUser, TRelationType> {

  @Override
  public void apply(@NotNull Update<? extends TUser, ? extends TRelationType> update) {
    validateUpdate(update);

    if (!Objects.equals(update.getWas(), getRelationType(update.getTarget())))
      throw new RuntimeException("Was and saved types differ for update " + update);

    if (update.getNow() == null) userRelations.remove(update.getTarget());
    else userRelations.put(update.getTarget(), singleton(update.getNow()));
  }

  @Override
  public @NotNull <TUpdate> Stream<TUpdate> updates(@NotNull RelationList<TUser, ? extends TRelationType> newer,
                                                    @NotNull UpdateFactory<? super TUser, ? super TRelationType, ? extends TUpdate> updateFactory) {
    return concat(users(), newer.users()).distinct()
        .filter(u -> !Objects.equals(getRelationType(u), newer.getRelationType(u)))
        .map(u -> updateFactory.create(u, getRelationType(u), newer.getRelationType(u)));
  }
}
