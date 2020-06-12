package musin.seeker.relation;

import lombok.Data;

@Data
public class TestRelationUpdate {
  private final String user;
  private final TestRelation was;
  private final TestRelation now;
}
