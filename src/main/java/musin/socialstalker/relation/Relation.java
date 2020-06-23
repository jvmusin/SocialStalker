package musin.socialstalker.relation;

public interface Relation<TRelationType> {
  User<?> getUser();

  TRelationType getType();
}
