package musin.stalker.relation;

import lombok.Data;
import musin.stalker.db.Id;

@Data
public class TestId implements Id {
  private final String value;
}
