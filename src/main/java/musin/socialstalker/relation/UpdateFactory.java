package musin.socialstalker.relation;

public interface UpdateFactory<TRelationType, TUpdate> {
  TUpdate updating(User<?> user, TRelationType was, TRelationType now);

  default TUpdate creating(User<?> user, TRelationType type) {
    return updating(user, null, type);
  }

  default TUpdate removing(User<?> user, TRelationType type) {
    return updating(user, type, null);
  }
}
