package musin.seeker.relation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RelationFactoryBase<ID, TUser, TRelationType, TRelation>
    implements RelationFactory<ID, TRelationType, TRelation> {
  private final UserFactory<ID, TUser> userUserFactory;

  protected TUser createUser(ID id) {
    return userUserFactory.create(id);
  }
}
