package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory
    implements RelationFactory<InstagramRelationType, InstagramRelation> {

  @Override
  public InstagramRelation create(User<?> user, InstagramRelationType type) {
    return new InstagramRelationImpl(user, type);
  }

  @Data
  private static class InstagramRelationImpl implements InstagramRelation {
    private final User<?> user;
    private final InstagramRelationType type;
  }
}
