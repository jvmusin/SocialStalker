package musin.seeker.relation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RelationFactoryBase<ID, TUser, TRelationType, TRelation>
    implements RelationFactory<ID, TUser, TRelationType, TRelation> {
  private final UserFactory<ID, TUser> userFactory;

  @Override
  public TRelation createById(ID id, TRelationType type) {
    return createByUser(userFactory.create(id), type);
  }
}
