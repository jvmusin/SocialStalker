package musin.stalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.stalker.config.NetworkNames;
import musin.stalker.db.model.Stalker;
import musin.stalker.relation.User;
import musin.stalker.vk.api.VkApi;
import musin.stalker.vk.api.VkID;
import musin.stalker.vk.api.VkIdFactory;
import musin.stalker.vk.db.VkMonitoringServiceFactory;
import musin.stalker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(NetworkNames.VK)
@RequiredArgsConstructor
public class VkNetworkFactory implements NetworkFactory {

  private final VkMonitoringServiceFactory monitoringServiceFactory;
  private final VkIdFactory idFactory;
  private final VkUserFactory userFactory;
  private final VkApi api;

  @Override
  public Network create(Stalker stalker) {
    return new VkNetwork(stalker);
  }

  private class VkNetwork extends NetworkBase<VkID> {
    public VkNetwork(Stalker stalker) {
      super(monitoringServiceFactory.create(stalker), idFactory, userFactory, api);
    }

    @Override
    public Optional<User> searchByUsernameOrId(String usernameOrId) {
      return searchByUsername(usernameOrId);
    }
  }
}
