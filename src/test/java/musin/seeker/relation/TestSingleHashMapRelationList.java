package musin.seeker.relation;

public class TestSingleHashMapRelationList
    extends SingleHashMapRelationList<TestUser, TestRelationType, TestRelation>
    implements TestRelationList {
  public TestSingleHashMapRelationList() {
    super(new TestRelationFactory());
  }
}
