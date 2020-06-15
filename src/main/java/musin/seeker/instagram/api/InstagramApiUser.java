package musin.seeker.instagram.api;

import lombok.Data;

@Data
public class InstagramApiUser {
  private final long userId;
  private final String username;
  private final String fullName;
}
