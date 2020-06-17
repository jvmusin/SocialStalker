package musin.seeker.relation;

public class TestUserFactory implements UserFactory<String, TestUser> {
  @Override
  public TestUser create(String id) {
    return new TestUser(id);
  }
}
