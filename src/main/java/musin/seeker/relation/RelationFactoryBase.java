package musin.seeker.relation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RelationFactoryBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TRelation>
    implements RelationFactory<ID, TUser, TRelationType, TRelation> {

  private final UserFactory<ID, TUser> userFactory;

  @Override
  public TRelation create(ID id, TRelationType type) {
    return create(userFactory.create(id), type);
  }
}
