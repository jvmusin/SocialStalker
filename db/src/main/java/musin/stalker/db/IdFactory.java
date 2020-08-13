package musin.stalker.db;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public interface IdFactory<ID extends Id> {
  ID parse(String id);

  default Optional<ID> tryParse(String id) {
    try {
      return of(parse(id));
    } catch (Exception e) {
      // parse unsuccessful
      return empty();
    }
  }
}
