package musin.socialstalker.vk.api;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class VkApiUser {
  private final VkID id;
  private final String nickname;
  private final String firstName;
  private final String lastName;

  public VkApiUser(VkID id, String nickname, String firstName, String lastName) {
    this.id = id;
    this.nickname = StringUtils.isEmpty(nickname) ? null : nickname;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
