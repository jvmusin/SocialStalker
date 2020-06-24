package musin.socialstalker.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateImpl implements Update {
  private final User suspected;
  private final RelationType was;
  private final RelationType now;
}
