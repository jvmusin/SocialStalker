package musin.socialstalker.relation;

import musin.socialstalker.relation.list.RelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TestRelationList extends RelationList<RelationType> {
  default @NotNull Stream<Update> updates(@NotNull RelationList<RelationType> newer) {
    return updates(newer, new TestUpdateFactory());
  }

  default @NotNull Stream<TestRelation> relations() {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> new TestRelation(u, t)));
  }
}
