package musin.seeker.vkseeker.relation;

public interface Relation<User, Type> {
  User getUser();

  Type getType();
}
