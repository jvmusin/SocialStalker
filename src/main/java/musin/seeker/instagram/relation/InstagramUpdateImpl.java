package musin.seeker.instagram.relation;

import lombok.Data;

@Data
public class InstagramUpdateImpl implements InstagramUpdate {
  private final InstagramUser target;
  private final InstagramRelation was;
  private final InstagramRelation now;
}
