package musin.socialstalker.relation;

import lombok.Data;

@Data
public class TestRelation implements Relation<TestUser, TestRelationType> {
  private final TestUser user;
  private final TestRelationType type;

  TestRelation(TestUser user, TestRelationType type) {
    this.user = user;
    this.type = type;
  }

  TestRelation(String user, String type) {
    this.user = new TestUser(user);
    this.type = new TestRelationType(type);
  }

  TestUpdate asAdd() {
    return new TestUpdate(user, null, type);
  }

  TestUpdate asRemove() {
    return new TestUpdate(user, type, null);
  }
}
