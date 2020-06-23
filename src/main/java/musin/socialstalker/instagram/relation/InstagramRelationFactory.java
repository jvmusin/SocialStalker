package musin.socialstalker.instagram.relation;

import lombok.Data;
import musin.socialstalker.relation.Relation;
import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.User;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory implements RelationFactory<RelationType> {

  @Override
  public Relation<RelationType> create(User<?> user, RelationType type) {
    return new InstagramRelationImpl(user, type);
  }

  @Data
  private static class InstagramRelationImpl implements InstagramRelation {
    private final User<?> user;
    private final RelationType type;
  }
}
