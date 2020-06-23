package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class InstagramUpdateFactory implements UpdateFactory {

  @Override
  public Update updating(User<?> user, RelationType was, RelationType now) {
    return new InstagramUpdateImpl(user, was, now);
  }

  @Data
  private static class InstagramUpdateImpl implements InstagramUpdate {
    private final User<?> suspected;
    private final RelationType was;
    private final RelationType now;
  }
}
