package musin.seeker.instagram.api;

import musin.seeker.db.IdFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramIdFactory implements IdFactory<InstagramID> {
  @Override
  public InstagramID parse(String id) {
    return new InstagramID(id);
  }
}
