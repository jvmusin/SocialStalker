package musin.socialstalker.relation;

import lombok.Data;

@Data
public class TestUpdate implements Update<TestUser, TestRelationType> {
  private final TestUser suspected;
  private final TestRelationType was;
  private final TestRelationType now;

  TestUpdate(TestUser suspected, TestRelationType was, TestRelationType now) {
    this.suspected = suspected;
    this.was = was;
    this.now = now;
  }

  TestUpdate(String suspected, String was, String now) {
    this.suspected = new TestUser(suspected);
    this.was = was == null ? null : new TestRelationType(was);
    this.now = now == null ? null : new TestRelationType(now);
  }
}
