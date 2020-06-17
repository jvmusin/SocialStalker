package musin.seeker.relation;

public class TestRelationFactory extends RelationFactoryBase<String, TestUser, TestRelationType, TestRelation> {

  public TestRelationFactory() {
    super(new TestUserFactory());
  }

  @Override
  public TestRelation create(TestUser user, TestRelationType type) {
    return new TestRelation(user, type);
  }
}
