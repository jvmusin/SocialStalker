package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.SocialApi;
import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.updater.MonitoringServiceFactory;
import org.springframework.stereotype.Component;

@Component(NetworkNames.INSTAGRAM)
@RequiredArgsConstructor
public class InstagramNetworkFactory implements NetworkFactory {

  private final MonitoringServiceFactory<InstagramID> seekerServiceFactory;
  private final IdFactory<InstagramID> idFactory;
  private final UserFactory<InstagramID> userFactory;
  private final SocialApi<InstagramID> api;

  @Override
  public Network create(Stalker stalker) {
    return new NetworkBase<>(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
  }
}
