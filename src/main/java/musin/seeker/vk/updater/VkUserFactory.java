package musin.seeker.vk.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.notifier.User;
import musin.seeker.vk.api.VkApi;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUserFactory {

  private final VkApi vkApi;

  public User createUser(int id) {
    return new VkUser(id);
  }

  @Data
  private class VkUser implements User {
    private final int id;

    @Override
    public String getName() {
      return vkApi.loadUser(id).toString();
    }

    @Override
    public String getLink() {
      return "https://vk.com/id" + id;
    }
  }
}
