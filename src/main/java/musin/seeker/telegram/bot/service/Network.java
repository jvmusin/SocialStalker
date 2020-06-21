package musin.seeker.telegram.bot.service;

import musin.seeker.relation.User;

import java.util.List;
import java.util.Optional;

public interface Network {
  Optional<User<?>> searchByUsername(String username);

  Optional<User<?>> searchById(String id);

  default Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
    return searchByUsername(usernameOrId)
        .or(() -> searchById(usernameOrId));
  }

  void add(String id);

  void delete(String id);

  List<User<?>> listTargets();
}
