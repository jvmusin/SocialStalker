package musin.seeker.relation;

public interface Update<TUser extends User<?>, TRelationType> {
  TUser getTarget();

  TRelationType getWas();

  TRelationType getNow();
}
