package musin.seeker.relation;

public interface RelationFactory<ID, TUser extends User<ID>, TRelationType, TRelation> {
  TRelation create(ID id, TRelationType type);

  TRelation create(TUser user, TRelationType type);
}
