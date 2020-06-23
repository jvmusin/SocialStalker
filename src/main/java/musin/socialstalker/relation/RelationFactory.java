package musin.socialstalker.relation;

public interface RelationFactory {
  Relation create(User<?> user, RelationType type);
}
