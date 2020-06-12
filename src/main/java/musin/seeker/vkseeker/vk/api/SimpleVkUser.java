package musin.seeker.vkseeker.vk.api;

import lombok.Data;

@Data
public class SimpleVkUser {
  private final int userId;
  private final String firstName;
  private final String lastName;

  @Override
  public String toString() {
    return String.format("%d: %s %s", userId, firstName, lastName);
  }
}