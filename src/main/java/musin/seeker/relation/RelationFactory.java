package musin.seeker.relation;

public interface RelationFactory<ID, TUser, TRelationType, TRelation> {
  TRelation createById(ID id, TRelationType type);

  TRelation createByUser(TUser user, TRelationType type);
}
