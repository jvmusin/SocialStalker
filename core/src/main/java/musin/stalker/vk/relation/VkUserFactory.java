package musin.stalker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.stalker.relation.LazyLoadingUser;
import musin.stalker.relation.User;
import musin.stalker.relation.UserFactory;
import musin.stalker.vk.api.VkApi;
import musin.stalker.vk.api.VkApiUser;
import musin.stalker.vk.api.VkID;
import musin.stalker.api.Id;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class VkUserFactory implements UserFactory<VkID> {

  private final VkApi vkApi;

  public User create(VkID id) {
    return new VkUser(
        id,
        () -> vkApi.getUser(id).orElseThrow(() -> new RuntimeException("User not found! (should never happen)"))
    );
  }

  private static class VkUser extends LazyLoadingUser<VkApiUser> {
    public VkUser(Id id, Supplier<VkApiUser> loadUser) {
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
