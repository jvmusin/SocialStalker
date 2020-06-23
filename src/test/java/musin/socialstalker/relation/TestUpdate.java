package musin.socialstalker.relation;

import lombok.Data;

@Data
public class TestUpdate implements Update {
  private final User<?> suspected;
  private final RelationType was;
  private final RelationType now;

  TestUpdate(User<?> suspected, RelationType was, RelationType now) {
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
