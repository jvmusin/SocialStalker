package musin.socialstalker.relation;

import lombok.Data;
import musin.socialstalker.api.Id;

@Data
public class TestId implements Id {
  private final String value;
}
