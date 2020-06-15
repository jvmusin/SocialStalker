package musin.seeker.relation;

public interface UserFactory<ID, TUser extends User<ID>> {
  TUser create(ID id);
}
