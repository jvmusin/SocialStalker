package musin.socialstalker.relation;

public interface UserFactory<ID, TUser> {
  TUser create(ID id);
}
