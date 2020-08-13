package musin.stalker.api;

import musin.stalker.db.Id;

import java.util.Optional;

public interface SocialApi<ID extends Id> {
  Optional<ID> searchByUsername(String username);

  Optional<ID> searchById(ID id);
}
