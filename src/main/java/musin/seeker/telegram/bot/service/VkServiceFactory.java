package musin.seeker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.NetworkNames;
import musin.seeker.db.model.Stalker;
import musin.seeker.relation.User;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerServiceFactory;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(NetworkNames.VK)
@RequiredArgsConstructor
public class VkServiceFactory implements ServiceFactory {

  private final VkSeekerServiceFactory seekerServiceFactory;
  private final VkIdFactory idFactory;
  private final VkUserFactory userFactory;
  private final VkApi api;

  @Override
  public Service create(Stalker stalker) {
    return new VkService(stalker);
  }

  class VkService extends ServiceBase<VkID, VkUser> {
    public VkService(Stalker stalker) {
      super(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
    }

    @Override
    public Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
      return searchByUsername(usernameOrId);
    }
  }
}
