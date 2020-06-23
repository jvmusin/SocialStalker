package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class InstagramUpdateFactory
    implements UpdateFactory<InstagramRelationType> {

  @Override
  public InstagramUpdate updating(User<?> user, InstagramRelationType was, InstagramRelationType now) {
    return new InstagramUpdateImpl(user, was, now);
  }

  @Data
  private static class InstagramUpdateImpl implements InstagramUpdate {
    private final User<?> suspected;
    private final InstagramRelationType was;
    private final InstagramRelationType now;
  }
}
