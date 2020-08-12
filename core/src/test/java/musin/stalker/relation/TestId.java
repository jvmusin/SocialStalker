package musin.stalker.relation;

import lombok.Data;
import musin.stalker.api.Id;

@Data
public class TestId implements Id {
  private final String value;
}
