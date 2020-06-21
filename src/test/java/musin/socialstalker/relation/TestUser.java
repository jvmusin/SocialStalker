package musin.socialstalker.relation;

import lombok.Data;

@Data
public class TestUser implements User<String> {
  private final String id;
  private final String fullyQualifiedName;

  public TestUser(String fullyQualifiedName) {
    this.fullyQualifiedName = fullyQualifiedName;
    this.id = "id" + fullyQualifiedName;
  }

  @Override
  public String getLink() {
    return "link_to_user_" + fullyQualifiedName;
  }
}
