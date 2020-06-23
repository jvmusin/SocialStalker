package musin.socialstalker.relation.list;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Stream.concat;

public abstract class SingleHashMapRelationList<TRelationType extends RelationType> extends HashMapRelationList<TRelationType> {

  @Override
  public void apply(Update update) {
    validateUpdate(update);

    if (!Objects.equals(update.getWas(), getRelationType(update.getSuspected())))
      throw new RuntimeException("Was and saved types differ for update " + update);

    if (update.getNow() == null) userRelations.remove(update.getSuspected());
    else userRelations.put(update.getSuspected(), singleton(update.getNow()));
  }

  @Override
  public Stream<Update> updates(RelationList<RelationType> newer,
                                UpdateFactory<RelationType> updateFactory) {
    return concat(users(), newer.users()).distinct()
        .filter(u -> !Objects.equals(getRelationType(u), newer.getRelationType(u)))
        .map(u -> updateFactory.updating(u, getRelationType(u), newer.getRelationType(u)));
  }
}
