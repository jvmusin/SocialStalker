package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

public class TestMultiHashMapRelationList extends MultiHashMapRelationList<TestUser, TestRelation, TestRelationUpdate> {
  @NotNull
  @Override
  protected TestRelationUpdate createUpdate(@NotNull TestUser user, TestRelation was, TestRelation now) {
    return new TestRelationUpdate(user, was, now);
  }
}
