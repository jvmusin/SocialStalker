package musin.seeker.relation;

import lombok.Data;

@Data
public class TestRelationUpdate implements Update<TestUser, TestRelation> {
  private final TestUser target;
  private final TestRelation was;
  private final TestRelation now;

  TestRelationUpdate(TestUser target, TestRelation was, TestRelation now) {
    this.target = target;
    this.was = was;
    this.now = now;
  }

  TestRelationUpdate(String target, String was, String now) {
    this.target = new TestUser(target);
    this.was = was == null ? null : new TestRelation(this.target, new TestRelationType(was));
    this.now = now == null ? null : new TestRelation(this.target, new TestRelationType(now));
  }
}
