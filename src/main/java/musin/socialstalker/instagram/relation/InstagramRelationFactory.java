package musin.socialstalker.instagram.relation;

import musin.socialstalker.relation.*;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory implements RelationFactory {
  @Override
  public Relation create(User user, RelationType type) {
    return new RelationImpl(user, type);
  }
}
