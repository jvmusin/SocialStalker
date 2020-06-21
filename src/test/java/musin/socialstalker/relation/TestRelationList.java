package musin.socialstalker.relation;

import musin.socialstalker.relation.list.RelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TestRelationList extends RelationList<TestUser, TestRelationType> {
  default @NotNull Stream<TestUpdate> updates(@NotNull RelationList<TestUser, ? extends TestRelationType> newer) {
    return updates(newer, new TestUpdateFactory());
  }

  default @NotNull Stream<TestRelation> relations() {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> new TestRelation(u, t)));
  }
}
