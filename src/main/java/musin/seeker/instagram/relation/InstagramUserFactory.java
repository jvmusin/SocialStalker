package musin.seeker.instagram.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramApiUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUserFactory {
  private final InstagramApi instagramApi;

  public InstagramUser create(long id) {
    return new InstagramUserImpl(id);
  }

  @Data
  private class InstagramUserImpl implements InstagramUser {
    private final long id;

    @Override
    public String getName() {
      InstagramApiUser user = instagramApi.loadUser(id);
      return String.format("%d: %s (%s)", user.getUserId(), user.getFullName(), user.getUsername());
    }

    @Override
    public String getLink() {
      InstagramApiUser user = instagramApi.loadUser(id);
      return "https://instagram.com/" + user.getUsername();
    }

    @Override
    public String toString() {
      return getName();
    }
  }
}
