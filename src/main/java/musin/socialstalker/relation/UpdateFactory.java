package musin.socialstalker.relation;

public interface UpdateFactory<TRelationType, TUpdate> {
  Update<TRelationType> updating(User<?> user, TRelationType was, TRelationType now);

  default Update<TRelationType> creating(User<?> user, TRelationType type) {
    return updating(user, null, type);
  }

  default Update<TRelationType> removing(User<?> user, TRelationType type) {
    return updating(user, type, null);
  }
}
