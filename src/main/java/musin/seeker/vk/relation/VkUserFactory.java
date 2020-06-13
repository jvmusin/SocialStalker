package musin.seeker.vk.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
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
