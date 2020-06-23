package musin.socialstalker.relation;

public class TestUpdateFactory implements UpdateFactory<TestRelationType, TestUpdate> {
  @Override
  public TestUpdate updating(User<?> user, TestRelationType was, TestRelationType now) {
    return new TestUpdate(user, was, now);
  }
}
