package musin.seeker.relation.v2;

import lombok.Data;
import musin.seeker.relation.Relation;

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

  TestRelationUpdate asAdd() {
    return new TestRelationUpdate(user, null, this);
  }

  TestRelationUpdate asRemove() {
    return new TestRelationUpdate(user, this, null);
  }
}
