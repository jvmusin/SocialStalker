package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.UpdateFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramUpdateFactory
    implements UpdateFactory<InstagramUser, InstagramRelationType, InstagramUpdate> {

  @Override
  public InstagramUpdate updating(InstagramUser user, InstagramRelationType was, InstagramRelationType now) {
    return new InstagramUpdateImpl(user, was, now);
  }

  @Data
  private static class InstagramUpdateImpl implements InstagramUpdate {
    private final InstagramUser suspected;
    private final InstagramRelationType was;
    private final InstagramRelationType now;
  }
}
