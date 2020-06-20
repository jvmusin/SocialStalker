package musin.seeker.telegram.bot.service;

import musin.seeker.config.ServiceNames;
import musin.seeker.instagram.api.InstagramApi;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.api.InstagramIdFactory;
import musin.seeker.instagram.db.InstagramSeekerService;
import musin.seeker.instagram.relation.InstagramUser;
import musin.seeker.instagram.relation.InstagramUserFactory;
import org.springframework.stereotype.Component;

@Component(ServiceNames.INSTAGRAM)
public class InstagramService extends ServiceBase<InstagramID, InstagramUser> {
  public InstagramService(InstagramSeekerService seekerService,
                          InstagramIdFactory idFactory,
                          InstagramUserFactory userFactory,
                          InstagramApi api) {
    super(seekerService, idFactory, userFactory, api);
  }
}
