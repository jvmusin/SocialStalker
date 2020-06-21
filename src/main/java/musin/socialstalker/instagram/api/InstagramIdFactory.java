package musin.socialstalker.instagram.api;

import musin.socialstalker.db.IdFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramIdFactory implements IdFactory<InstagramID> {
  @Override
  public InstagramID parse(String id) {
    return new InstagramID(id);
  }
}
