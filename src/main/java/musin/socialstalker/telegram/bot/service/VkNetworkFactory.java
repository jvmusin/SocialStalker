package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.SocialApi;
import musin.socialstalker.config.NetworkNames;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.updater.MonitoringServiceFactory;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.relation.VkUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(NetworkNames.VK)
@RequiredArgsConstructor
public class VkNetworkFactory implements NetworkFactory {

  private final MonitoringServiceFactory<VkID> monitoringServiceFactory;
  private final IdFactory<VkID> idFactory;
  private final UserFactory<VkID> userFactory;
  private final SocialApi<VkID> api;

  @Override
  public Network create(Stalker stalker) {
    return new VkNetwork(stalker);
  }

  private class VkNetwork extends NetworkBase<VkID, VkUser> {
    public VkNetwork(Stalker stalker) {
      super(monitoringServiceFactory.create(stalker), idFactory, userFactory, api);
    }

    @Override
    public Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
      return searchByUsername(usernameOrId);
    }
  }
}
