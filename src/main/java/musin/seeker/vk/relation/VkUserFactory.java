package musin.seeker.vk.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.vk.api.SimpleVkUser;
import musin.seeker.vk.api.VkApi;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUserFactory {

  private final VkApi vkApi;

  public VkUser create(int id) {
    return new VkUserImpl(id);
  }

  @Data
  private class VkUserImpl implements VkUser {
    private final Integer id;

    @Override
    public String getName() {
      SimpleVkUser user = vkApi.loadUser(id);
      return String.format("%d: %s %s", user.getUserId(), user.getFirstName(), user.getLastName());
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
