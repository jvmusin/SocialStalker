package musin.socialstalker.instagram.relation;

import musin.socialstalker.relation.*;
import org.springframework.stereotype.Component;

@Component
public class InstagramUpdateFactory implements UpdateFactory {
  @Override
  public Update updating(User user, RelationType was, RelationType now) {
    return new UpdateImpl(user, was, now);
  }
}
