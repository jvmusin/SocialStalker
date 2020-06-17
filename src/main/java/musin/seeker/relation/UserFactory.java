package musin.seeker.relation;

public interface UserFactory<ID, TUser> {
  TUser create(ID id);
}
