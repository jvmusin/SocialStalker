package musin.stalker.relation;

import musin.stalker.db.Id;

public interface UserFactory<ID extends Id> {
  User create(ID id);
}
