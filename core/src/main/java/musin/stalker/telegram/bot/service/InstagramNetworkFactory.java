package musin.stalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.stalker.config.NetworkNames;
import musin.stalker.db.model.Stalker;
import musin.stalker.instagram.api.InstagramApi;
import musin.stalker.instagram.api.InstagramIdFactory;
import musin.stalker.instagram.db.InstagramMonitoringServiceFactory;
import musin.stalker.instagram.relation.InstagramUserFactory;
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
