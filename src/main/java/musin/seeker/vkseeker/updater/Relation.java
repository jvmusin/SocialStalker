package musin.seeker.vkseeker.updater;

public interface Relation<User, Type> {
  User getUser();

  Type getType();
}
