package musin.stalker.relation;

import musin.stalker.api.Id;

public interface UserFactory<ID extends Id> {
  User create(ID id);
}
