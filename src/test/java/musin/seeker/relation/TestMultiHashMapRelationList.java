package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

public class TestMultiHashMapRelationList extends MultiHashMapRelationList<TestUser, TestRelationType, TestRelation, TestUpdate> {
  @Override
  protected @NotNull TestUpdate createUpdate(@NotNull TestUser user, TestRelationType was, TestRelationType now) {
    return new TestUpdate(user, was, now);
  }

  @Override
  protected @NotNull TestRelation createRelation(@NotNull TestUser user, TestRelationType type) {
    return new TestRelation(user, type);
  }
}
