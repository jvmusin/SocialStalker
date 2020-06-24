package musin.socialstalker.relation;

public interface UpdateFactory {
  Update updating(User user, RelationType was, RelationType now);

  default Update creating(User user, RelationType type) {
    return updating(user, null, type);
  }

  default Update removing(User user, RelationType type) {
    return updating(user, type, null);
  }
}
