package musin.seeker.relation;

public class TestMultiHashMapRelationList extends MultiHashMapRelationList<TestUser, TestRelationType, TestRelation, TestUpdate> {
  public TestMultiHashMapRelationList() {
    super(TestUpdate::new, new RelationFactory<>() {
      @Override
      public TestRelation create(String id, TestRelationType type) {
        return new TestRelation(new TestUser(id), type);
      }

      @Override
      public TestRelation create(TestUser user, TestRelationType type) {
        return new TestRelation(user, type);
      }
    });
  }
}
