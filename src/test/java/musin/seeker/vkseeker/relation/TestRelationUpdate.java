package musin.seeker.vkseeker.relation;

import lombok.Data;

@Data
public class TestRelationUpdate {
  private final String user;
  private final TestRelation was;
  private final TestRelation now;
}
