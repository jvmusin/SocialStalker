package musin.socialstalker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.relation.LazyLoadingUser;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.vk.api.VkApi;
import musin.socialstalker.vk.api.VkApiUser;
import musin.socialstalker.vk.api.VkID;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class VkUserFactory implements UserFactory<VkID> {

  private final VkApi vkApi;

  public VkUser create(VkID id) {
    return new VkUserImpl(id, () -> vkApi.getUser(id).orElseThrow(() -> new RuntimeException("User not found! (should never happen)")));
  }

  private static class VkUserImpl extends LazyLoadingUser<VkID, VkApiUser> implements VkUser {
    public VkUserImpl(VkID id, Supplier<VkApiUser> loadUser) {
      super(id, loadUser);
    }

    @Override
    public String getFullyQualifiedName() {
      return String.format("%s: %s %s (%s)", user().getId(), user().getFirstName(), user().getLastName(), user().getNickname());
    }

    @Override
    public String getLink() {
      return "https://vk.com/id" + getId();
    }
  }
}
