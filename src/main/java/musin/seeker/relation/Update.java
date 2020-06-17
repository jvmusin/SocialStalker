package musin.seeker.relation;

public interface Update<TUser, TRelationType> {
  TUser getTarget();

  TRelationType getWas();

  TRelationType getNow();
}
