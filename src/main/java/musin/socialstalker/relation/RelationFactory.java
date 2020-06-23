package musin.socialstalker.relation;

public interface RelationFactory<TRelationType, TRelation> {
  TRelation create(User<?> user, TRelationType type);
}
