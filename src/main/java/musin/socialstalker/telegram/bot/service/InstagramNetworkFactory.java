package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.vk.api.VkApi;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.db.VkSeekerServiceFactory;
import musin.socialstalker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component(NetworkNames.INSTAGRAM)
@RequiredArgsConstructor
public class InstagramNetworkFactory implements NetworkFactory {

  private final VkSeekerServiceFactory seekerServiceFactory;
  private final VkIdFactory idFactory;
  private final VkUserFactory userFactory;
  private final VkApi api;

  @Override
  public Network create(Stalker stalker) {
    return new NetworkBase<>(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
  }
}
