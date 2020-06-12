package musin.seeker.relation;

public interface Relation<TUser, TRelationType> {
  TUser getUser();

  TRelationType getType();
}
