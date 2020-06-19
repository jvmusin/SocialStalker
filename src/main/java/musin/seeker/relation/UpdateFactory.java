package musin.seeker.relation;

public interface UpdateFactory<TUser, TRelationType, TUpdate> {
  TUpdate updating(TUser user, TRelationType was, TRelationType now);

  default TUpdate creating(TUser user, TRelationType type) {
    return updating(user, null, type);
  }

  default TUpdate removing(TUser user, TRelationType type) {
    return updating(user, type, null);
  }
}
