package musin.socialstalker.relation;

import musin.socialstalker.relation.list.RelationList;

import java.util.stream.Stream;

public interface TestRelationList extends RelationList {
  default Stream<Update> updates(RelationList newer) {
    return updates(newer, new TestUpdateFactory());
  }

  default Stream<TestRelation> relations() {
    return users().flatMap(u -> getAllRelationTypes(u).stream().map(t -> new TestRelation(u, t)));
  }
}
