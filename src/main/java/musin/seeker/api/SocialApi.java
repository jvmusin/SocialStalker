package musin.seeker.api;

import java.util.Optional;

public interface SocialApi<ID> {
  Optional<ID> searchByUsername(String username);

  Optional<ID> searchById(ID id);
}
