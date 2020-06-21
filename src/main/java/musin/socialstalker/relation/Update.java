package musin.socialstalker.relation;

public interface Update<TUser, TRelationType> {
  TUser getSuspected();

  TRelationType getWas();

  TRelationType getNow();
}
