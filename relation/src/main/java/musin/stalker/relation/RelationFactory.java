package musin.stalker.relation;

public interface RelationFactory {
  Relation create(User user, RelationType type);
}
