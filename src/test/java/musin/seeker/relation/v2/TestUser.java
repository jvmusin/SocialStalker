package musin.seeker.relation.v2;

import lombok.Data;
import musin.seeker.notifier.User;

@Data
public class TestUser implements User {
  private final String name;

  @Override
  public String getLink() {
    return "link_to_user_" + name;
  }
}
