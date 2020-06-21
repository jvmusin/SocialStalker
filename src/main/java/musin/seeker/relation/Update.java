package musin.seeker.relation;

public interface Update<TUser, TRelationType> {
  TUser getSuspected();

  TRelationType getWas();

  TRelationType getNow();
}
