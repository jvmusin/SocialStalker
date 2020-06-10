package musin.seeker.vkseeker.updater.v3;

public interface Relation<User, Type> {
  User getUser();

  Type getType();
}
