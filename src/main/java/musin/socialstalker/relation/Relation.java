package musin.socialstalker.relation;

public interface Relation<TUser, TRelationType> {
  TUser getUser();

  TRelationType getType();
}
