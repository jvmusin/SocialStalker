package musin.seeker.vkseeker.relation;

public interface Relation<TUser, TRelationType> {
  TUser getUser();

  TRelationType getType();
}
