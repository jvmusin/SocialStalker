package musin.socialstalker.relation;

public class TestUpdateFactory implements UpdateFactory {
  @Override
  public TestUpdate updating(User user, RelationType was, RelationType now) {
    return new TestUpdate(user, was, now);
  }
}
