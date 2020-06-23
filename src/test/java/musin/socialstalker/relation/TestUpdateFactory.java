package musin.socialstalker.relation;

public class TestUpdateFactory implements UpdateFactory<TestRelationType, Update<TestRelationType>> {
  @Override
  public TestUpdate updating(User<?> user, TestRelationType was, TestRelationType now) {
    return new TestUpdate(user, was, now);
  }
}
