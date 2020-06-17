package musin.seeker.relation;

public interface RelationFactory<ID, TRelationType, TRelation> {
  TRelation create(ID id, TRelationType type);
}
