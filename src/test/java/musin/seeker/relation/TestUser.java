package musin.seeker.relation;

import lombok.Data;

@Data
public class TestUser implements User<String> {
  private final String id;
  private final String name;

  public TestUser(String name) {
    this.name = name;
    this.id = "id" + name;
  }

  @Override
  public String getLink() {
    return "link_to_user_" + name;
  }
}
