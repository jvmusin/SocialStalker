package musin.seeker.relation;

public class TestMultiHashMapRelationList extends MultiHashMapRelationList<TestUser, TestRelationType, TestRelation, TestUpdate> {
  public TestMultiHashMapRelationList() {
    super(new TestUpdateFactory(), new TestRelationFactory());
  }
}
