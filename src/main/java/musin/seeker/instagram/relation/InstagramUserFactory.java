package musin.seeker.instagram.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramApiUser;
import musin.seeker.relation.UserFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("instagram")
@RequiredArgsConstructor
public class InstagramUserFactory implements UserFactory<InstagramID, InstagramUser> {
  private final InstagramApi instagramApi;

  @Override
  public InstagramUser create(InstagramID id) {
    return new InstagramUserImpl(id);
  }

  @Data
  private class InstagramUserImpl implements InstagramUser {
    private final InstagramID id;

    @Override
    public String getName() {
      InstagramApiUser user = instagramApi.loadUser(id.getValue());
      return String.format("%d: %s (%s)", user.getUserId(), user.getFullName(), user.getUsername());
    }

    @Override
    public String getLink() {
      InstagramApiUser user = instagramApi.loadUser(id.getValue());
      return "https://instagram.com/" + user.getUsername();
    }

    @Override
    public String toString() {
      return getName();
    }
  }
}
