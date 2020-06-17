package musin.seeker.relation;

public interface UpdateFactory<TUser, TRelationType, TUpdate> {
  TUpdate create(TUser user, TRelationType was, TRelationType now);
}
