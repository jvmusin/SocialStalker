package musin.seeker.relation;

import lombok.Data;

@Data
public class TestRelation implements Relation<String, String> {
  private final String user;
  private final String type;
}
