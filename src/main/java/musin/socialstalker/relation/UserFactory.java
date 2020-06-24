package musin.socialstalker.relation;

import musin.socialstalker.api.Id;

public interface UserFactory<ID extends Id> {
  User create(ID id);
}
