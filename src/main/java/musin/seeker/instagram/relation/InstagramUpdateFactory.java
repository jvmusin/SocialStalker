package musin.seeker.instagram.relation;

import lombok.Data;
import musin.seeker.relation.UpdateFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramUpdateFactory implements UpdateFactory<InstagramUser, InstagramRelationType, InstagramUpdate> {
  @Override
  public InstagramUpdate create(InstagramUser user, InstagramRelationType was, InstagramRelationType now) {
    return new InstagramUpdateImpl(user, was, now);
  }

  @Data
  private static class InstagramUpdateImpl implements InstagramUpdate {
    private final InstagramUser target;
    private final InstagramRelationType was;
    private final InstagramRelationType now;
  }
}
