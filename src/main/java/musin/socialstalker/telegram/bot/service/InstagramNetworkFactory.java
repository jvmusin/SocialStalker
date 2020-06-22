package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramApi;
import musin.socialstalker.instagram.api.InstagramIdFactory;
import musin.socialstalker.instagram.db.InstagramMonitoringServiceFactory;
import musin.socialstalker.instagram.relation.InstagramUserFactory;
import org.springframework.stereotype.Component;

@Component(NetworkNames.INSTAGRAM)
@RequiredArgsConstructor
public class InstagramNetworkFactory implements NetworkFactory {

  private final InstagramMonitoringServiceFactory seekerServiceFactory;
  private final InstagramIdFactory idFactory;
  private final InstagramUserFactory userFactory;
  private final InstagramApi api;

  @Override
  public Network create(Stalker stalker) {
    return new NetworkBase<>(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
  }
}
