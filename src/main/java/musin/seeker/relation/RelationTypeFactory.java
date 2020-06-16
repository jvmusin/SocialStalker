package musin.seeker.relation;

public interface RelationTypeFactory<T> {
  T parseNullSafe(String type);
}
