package musin.seeker.instagram.db;

import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.api.InstagramIdFactory;
import musin.seeker.instagram.config.InstagramServiceProperties;
import musin.seeker.updater.SeekerServiceBase;
import org.springframework.stereotype.Service;

@Service
public class InstagramSeekerService extends SeekerServiceBase<InstagramID> {
  public InstagramSeekerService(SeekerRepository seekerRepository,
                                InstagramServiceProperties properties,
                                InstagramIdFactory idFactory) {
    super(seekerRepository, properties, idFactory);
  }
}
