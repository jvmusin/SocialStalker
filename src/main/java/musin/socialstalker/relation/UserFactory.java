package musin.socialstalker.relation;

public interface UserFactory<ID> {
  User<?> create(ID id);
}
