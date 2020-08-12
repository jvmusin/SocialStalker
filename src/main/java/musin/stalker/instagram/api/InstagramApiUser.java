package musin.stalker.instagram.api;

import lombok.Data;

@Data
public class InstagramApiUser {
  private final InstagramID id;
  private final String username;
  private final String fullName;
}
