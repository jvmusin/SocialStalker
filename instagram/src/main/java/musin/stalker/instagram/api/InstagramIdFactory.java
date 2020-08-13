package musin.stalker.instagram.api;

import musin.stalker.api.IdFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramIdFactory implements IdFactory<InstagramID> {
  @Override
  public InstagramID parse(String id) {
    return new InstagramID(id);
  }
}
