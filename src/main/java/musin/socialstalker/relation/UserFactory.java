package musin.socialstalker.relation;

public interface UserFactory<ID> {
  User<ID> create(ID id);
}
