package musin.seeker.instagram.api;

import lombok.Data;
import musin.seeker.instagram.relation.InstagramID;

@Data
public class InstagramApiUser {
  private final InstagramID id;
  private final String username;
  private final String fullName;
}
