package musin.socialstalker.relation;

public interface UpdateFactory<TRelationType> {
  Update<? extends TRelationType> updating(User<?> user, TRelationType was, TRelationType now);

  default Update<? extends TRelationType> creating(User<?> user, TRelationType type) {
    return updating(user, null, type);
  }

  default Update<? extends TRelationType> removing(User<?> user, TRelationType type) {
    return updating(user, type, null);
  }
}
