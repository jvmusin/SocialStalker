package musin.seeker.telegram.bot.service;

import musin.seeker.config.ServiceNames;
import musin.seeker.relation.User;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(ServiceNames.VK)
public class VkService extends ServiceBase<VkID, VkUser> {
  public VkService(VkSeekerService seekerService,
                   VkIdFactory idFactory,
                   VkUserFactory userFactory,
                   VkApi api) {
    super(seekerService, idFactory, userFactory, api);
  }

  @Override
  public Optional<User<?>> searchByUsernameOrId(String usernameOrId) {
    return searchByUsername(usernameOrId);
  }
}
