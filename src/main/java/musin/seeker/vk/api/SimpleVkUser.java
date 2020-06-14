package musin.seeker.vk.api;

import lombok.Data;

@Data
public class SimpleVkUser {
  private final int userId;
  private final String firstName;
  private final String lastName;
}