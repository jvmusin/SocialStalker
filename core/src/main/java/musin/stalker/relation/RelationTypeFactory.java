package musin.stalker.relation;

public interface RelationTypeFactory {
  RelationType parseNullSafe(String type);
}
