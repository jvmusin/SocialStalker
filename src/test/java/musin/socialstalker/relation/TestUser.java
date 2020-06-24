package musin.socialstalker.relation;

import lombok.Data;

@Data
public class TestUser implements User<TestId> {
  private final TestId id;
  private final String fullyQualifiedName;

  public TestUser(String fullyQualifiedName) {
    this.fullyQualifiedName = fullyQualifiedName;
    this.id = new TestId("id" + fullyQualifiedName);
  }

  @Override
  public String getLink() {
    return "link_to_user_" + fullyQualifiedName;
  }
}
