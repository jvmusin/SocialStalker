package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestTreeMapRelationList extends TreeMapRelationList<String, TestRelation, TestRelationUpdate> {

  TestTreeMapRelationList() {
  }

  TestTreeMapRelationList(List<TestRelation> relations) {
    addAll(relations);
  }

  @Override
  protected TestRelationUpdate createUpdate(@NotNull String user, TestRelation was, TestRelation now) {
    return new TestRelationUpdate(user, was, now);
  }
}
