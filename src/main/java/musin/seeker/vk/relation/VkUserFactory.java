package musin.seeker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.LazyLoadingUser;
import musin.seeker.relation.UserFactory;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkApiUser;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class VkUserFactory implements UserFactory<VkID, VkUser> {

  private final VkApi vkApi;

  public VkUser create(VkID id) {
    return new VkUserImpl(id, uid -> vkApi.getUser(uid).orElseThrow(() -> new RuntimeException("User not found! (should never happen)")));
  }

  private static class VkUserImpl extends LazyLoadingUser<VkID, VkApiUser> implements VkUser {
    public VkUserImpl(VkID instagramID, Function<VkID, VkApiUser> loadUser) {
      super(instagramID, loadUser);
    }

    @Override
    public String getFullyQualifiedName() {
      return String.format("%s: %s %s", user().getId(), user().getFirstName(), user().getLastName());
    }

    @Override
    public String getLink() {
      return "https://vk.com/id" + getId();
    }
  }
}
