package musin.socialstalker.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RelationImpl implements Relation {
  private final User user;
  private final RelationType type;
}
