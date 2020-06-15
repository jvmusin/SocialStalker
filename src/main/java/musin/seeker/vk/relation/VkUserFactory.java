package musin.seeker.vk.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.relation.UserFactory;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkApiUser;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUserFactory implements UserFactory<VkID, VkUser> {

  private final VkApi vkApi;

  public VkUser create(VkID id) {
    return new VkUserImpl(id);
  }

  @Data
  private class VkUserImpl implements VkUser {
    private final VkID id;

    @Override
    public String getName() {
      VkApiUser user = vkApi.loadUser(id);
      return String.format("%s: %s %s", user.getId(), user.getFirstName(), user.getLastName());
    }

    @Override
    public String getLink() {
      return "https://vk.com/id" + id;
    }

    @Override
    public String toString() {
      return getName();
    }
  }
}
