package musin.socialstalker.relation;

import musin.socialstalker.relation.list.RelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TestRelationList extends RelationList<TestRelationType> {
  default @NotNull Stream<? extends Update<? extends TestRelationType>> updates(@NotNull RelationList<TestRelationType> newer) {
    return updates(newer, new TestUpdateFactory());
  }

  default @NotNull Stream<? extends TestRelation> relations() {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> new TestRelation(u, t)));
  }
}
