package musin.seeker.relation;

public class TestSingleHashMapRelationList extends SingleHashMapRelationList<TestUser, TestRelationType, TestRelation, TestUpdate> {

  public TestSingleHashMapRelationList() {
    super(TestUpdate::new, new RelationFactory<String, TestUser, TestRelationType, TestRelation>() {
      @Override
      public TestRelation createById(String id, TestRelationType type) {
        return new TestRelation(new TestUser(id), type);
      }

      @Override
      public TestRelation createByUser(TestUser user, TestRelationType type) {
        return new TestRelation(user, type);
      }
    });
  }
}
