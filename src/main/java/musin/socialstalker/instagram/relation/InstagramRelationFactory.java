package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.RelationFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory
    implements RelationFactory<InstagramUser, InstagramRelationType, InstagramRelation> {

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
