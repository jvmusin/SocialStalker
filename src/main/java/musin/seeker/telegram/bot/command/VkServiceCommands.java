package musin.seeker.telegram.bot.command;

import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component
public class VkServiceCommands extends BasicServiceCommands<VkID, VkUser> {
  public VkServiceCommands(VkSeekerService seekerService,
                           VkIdFactory idFactory,
                           VkUserFactory userFactory,
                           VkApi api) {
    super("vk", seekerService, idFactory, userFactory, api);
  }
}
