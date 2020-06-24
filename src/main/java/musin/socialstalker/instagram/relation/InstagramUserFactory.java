package musin.socialstalker.instagram.relation;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.instagram.api.InstagramApi;
import musin.socialstalker.instagram.api.InstagramApiUser;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.relation.LazyLoadingUser;
import musin.socialstalker.relation.UserFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class InstagramUserFactory implements UserFactory<InstagramID> {
  private final InstagramApi instagramApi;

  @Override
  public InstagramUser create(InstagramID id) {
    return new InstagramUserImpl(id, () -> instagramApi.getUserInfo(id));
  }

  private static class InstagramUserImpl extends LazyLoadingUser<InstagramID, InstagramApiUser> implements InstagramUser {
    InstagramUserImpl(InstagramID id, Supplier<InstagramApiUser> loadUser) {
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
