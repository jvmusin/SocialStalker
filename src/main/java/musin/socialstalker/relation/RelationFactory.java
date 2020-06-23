package musin.socialstalker.relation;

public interface RelationFactory<TRelationType> {
  Relation<TRelationType> create(User<?> user, TRelationType type);
}
