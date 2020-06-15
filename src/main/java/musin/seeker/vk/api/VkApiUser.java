package musin.seeker.vk.api;

import lombok.Data;

@Data
public class VkApiUser {
  private final VkID id;
  private final String firstName;
  private final String lastName;
}
