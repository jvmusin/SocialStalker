package musin.socialstalker.relation;

import org.springframework.stereotype.Component;

@Component
public class RelationFactoryImpl implements RelationFactory {
  @Override
  public Relation create(User user, RelationType type) {
    return new RelationImpl(user, type);
  }
}
