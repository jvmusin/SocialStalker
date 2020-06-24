package musin.socialstalker.relation;

public class RelationFactoryImpl implements RelationFactory {
  @Override
  public Relation create(User user, RelationType type) {
    return new RelationImpl(user, type);
  }
}
