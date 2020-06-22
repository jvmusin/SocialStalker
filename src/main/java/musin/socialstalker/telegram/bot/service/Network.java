package musin.socialstalker.telegram.bot.service;

import musin.socialstalker.relation.User;

import java.util.List;
import java.util.Optional;

public interface Network {
  Optional<User<?>> searchByUsername(String username);

  Optional<User<?>> searchById(String id);

  default Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
    return searchByUsername(usernameOrId)
        .or(() -> searchById(usernameOrId));
  }

  void addMonitoring(String targetUsernameOrId);

  void deleteMonitoring(String targetUsernameOrId);

  List<User<?>> listTargets();
}
