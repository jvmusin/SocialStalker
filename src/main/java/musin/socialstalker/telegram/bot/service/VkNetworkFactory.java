package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.relation.User;
import musin.socialstalker.vk.api.VkApi;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.db.VkSeekerServiceFactory;
import musin.socialstalker.vk.relation.VkUser;
import musin.socialstalker.vk.relation.VkUserFactory;
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
