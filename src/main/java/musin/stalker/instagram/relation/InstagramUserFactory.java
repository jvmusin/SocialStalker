package musin.stalker.instagram.relation;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.instagram.api.InstagramApi;
import musin.stalker.instagram.api.InstagramApiUser;
import musin.stalker.instagram.api.InstagramID;
import musin.stalker.relation.LazyLoadingUser;
import musin.stalker.relation.User;
import musin.stalker.relation.UserFactory;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class InstagramUserFactory implements UserFactory<InstagramID> {
  private final InstagramApi instagramApi;

  @Override
  public User create(InstagramID id) {
    return new InstagramUserImpl(id, () -> instagramApi.getUserInfo(id));
  }

  private static class InstagramUserImpl extends LazyLoadingUser<InstagramApiUser> {
    InstagramUserImpl(Id id, Supplier<InstagramApiUser> loadUser) {
      super(id, loadUser);
    }

    @Override
    public String getFullyQualifiedName() {
      return String.format("%s: %s (%s)", user().getId(), user().getFullName(), user().getUsername());
    }

    @Override
    public String getLink() {
      return "https://instagram.com/" + user().getUsername();
    }
  }
}
