package musin.socialstalker.relation;

public interface RelationTypeFactory<T> {
  T parseNullSafe(String type);
}
