package musin.seeker.instagram.relation;

import lombok.Data;
import musin.seeker.relation.Relation;

@Data
public class InstagramRelation implements Relation<InstagramUser, InstagramRelationType> {
  private final InstagramUser user;
  private final InstagramRelationType type;
}
