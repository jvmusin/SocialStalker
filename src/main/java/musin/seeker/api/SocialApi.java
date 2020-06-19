package musin.seeker.api;

public interface SocialApi<ID> {
  ID searchByUsername(String username);
}
