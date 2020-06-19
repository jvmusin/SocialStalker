package musin.seeker.relation;

public class TestUpdateFactory implements UpdateFactory<TestUser, TestRelationType, TestUpdate> {
  @Override
  public TestUpdate updating(TestUser user, TestRelationType was, TestRelationType now) {
    return new TestUpdate(user, was, now);
  }
}
