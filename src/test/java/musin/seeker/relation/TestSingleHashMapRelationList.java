package musin.seeker.relation;

public class TestSingleHashMapRelationList extends SingleHashMapRelationList<TestUser, TestRelationType, TestRelation, TestUpdate> {

  public TestSingleHashMapRelationList() {
    super(new TestUpdateFactory(), new TestRelationFactory());
  }
}
