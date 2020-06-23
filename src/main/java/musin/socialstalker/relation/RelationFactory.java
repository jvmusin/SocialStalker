package musin.socialstalker.relation;

public interface RelationFactory<TRelationType> {
  Relation<? extends TRelationType> create(User<?> user, TRelationType type);
}
