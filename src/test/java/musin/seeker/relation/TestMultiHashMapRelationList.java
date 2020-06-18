package musin.seeker.relation;

public class TestMultiHashMapRelationList
    extends MultiHashMapRelationList<TestUser, TestRelationType, TestRelation>
    implements TestRelationList {
  public TestMultiHashMapRelationList() {
    super(new TestRelationFactory());
  }
}
