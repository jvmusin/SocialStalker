package musin.socialstalker.relation;

import musin.socialstalker.relation.list.RelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TestRelationList extends RelationList<TestRelationType> {
  default @NotNull Stream<TestUpdate> updates(@NotNull RelationList<? extends TestRelationType> newer) {
    return updates(newer, new TestUpdateFactory());
  }

  default @NotNull Stream<TestRelation> relations() {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> {
      User<?> u1 = u;
      TestRelationType t1 = t;
      return new TestRelation(u1, t1);
    }));
  }
}
