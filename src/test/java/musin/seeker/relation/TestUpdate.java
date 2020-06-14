package musin.seeker.relation;

import lombok.Data;

@Data
public class TestUpdate implements Update<TestUser, TestRelationType> {
  private final TestUser target;
  private final TestRelationType was;
  private final TestRelationType now;

  TestUpdate(TestUser target, TestRelationType was, TestRelationType now) {
    this.target = target;
    this.was = was;
    this.now = now;
  }

  TestUpdate(String target, String was, String now) {
    this.target = new TestUser(target);
    this.was = was == null ? null : new TestRelationType(was);
    this.now = now == null ? null : new TestRelationType(now);
  }
}
