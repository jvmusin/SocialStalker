package musin.seeker.relation;

import org.jetbrains.annotations.NotNull;

public class TestSingleHashMapRelationList extends SingleHashMapRelationList<TestUser, TestRelationType, TestRelation, TestRelationUpdate> {

  @Override
  protected @NotNull TestRelationUpdate createUpdate(@NotNull TestUser user, TestRelationType was, TestRelationType now) {
    return new TestRelationUpdate(user, was, now);
  }

  @Override
  protected @NotNull TestRelation createRelation(@NotNull TestUser user, TestRelationType type) {
    return new TestRelation(user, type);
  }
}
