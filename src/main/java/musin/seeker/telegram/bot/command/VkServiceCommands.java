package musin.seeker.telegram.bot.command;

import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerService;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component
public class VkServiceCommands extends BasicServiceCommands<VkID> {
  public VkServiceCommands(VkSeekerService seekerService, VkIdFactory idFactory, VkUserFactory userFactory) {
    super("vk", seekerService, idFactory, userFactory);
  }
}
