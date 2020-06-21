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
public class VkNetworkFactory implements NetworkFactory {

  private final VkSeekerServiceFactory seekerServiceFactory;
  private final VkIdFactory idFactory;
  private final VkUserFactory userFactory;
  private final VkApi api;

  @Override
  public Network create(Stalker stalker) {
    return new VkNetwork(stalker);
  }

  private class VkNetwork extends NetworkBase<VkID, VkUser> {
    public VkNetwork(Stalker stalker) {
      super(seekerServiceFactory.create(stalker), idFactory, userFactory, api);
    }

    @Override
    public Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
      return searchByUsername(usernameOrId);
    }
  }
}
