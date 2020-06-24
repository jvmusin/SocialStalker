package musin.socialstalker.vk.relation;

import musin.socialstalker.relation.*;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateFactory implements UpdateFactory {
  @Override
  public Update updating(User user, RelationType was, RelationType now) {
    return new UpdateImpl(user, was, now);
  }
}
