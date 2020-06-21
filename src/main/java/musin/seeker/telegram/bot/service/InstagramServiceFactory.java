package musin.seeker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.NetworkNames;
import musin.seeker.db.model.Stalker;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerServiceFactory;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component(NetworkNames.INSTAGRAM)
@RequiredArgsConstructor
public class InstagramServiceFactory implements ServiceFactory {

  private final VkSeekerServiceFactory seekerServiceFactory;
  private final VkIdFactory idFactory;
  private final VkUserFactory userFactory;
  private final VkApi api;

  @Override
  public Service create(Stalker stalker) {
    return new ServiceBase<>(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
  }
}
