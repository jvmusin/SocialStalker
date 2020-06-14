package musin.seeker.relation;

public interface RelationUpdate<TUser extends User, TRelationType> {
  TUser getTarget();

  TRelationType getWas();

  TRelationType getNow();
}
