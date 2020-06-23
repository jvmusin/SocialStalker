package musin.socialstalker.relation;

public interface RelationTypeFactory {
  RelationType parseNullSafe(String type);
}
