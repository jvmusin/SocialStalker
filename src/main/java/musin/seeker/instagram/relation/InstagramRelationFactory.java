package musin.seeker.instagram.relation;

import lombok.Data;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.relation.RelationFactoryBase;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory extends RelationFactoryBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramRelation> {

  public InstagramRelationFactory(InstagramUserFactory userFactory) {
    super(userFactory);
  }

  @Override
  public InstagramRelation create(InstagramUser user, InstagramRelationType type) {
    return new InstagramRelationImpl(user, type);
  }

  @Data
  private static class InstagramRelationImpl implements InstagramRelation {
    private final InstagramUser user;
    private final InstagramRelationType type;
  }
}
