package musin.seeker.telegram.bot.command;

import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.db.VkSeekerService;
import org.springframework.stereotype.Component;

@Component
public class VkServiceCommands extends BasicServiceCommands<VkID> {
  public VkServiceCommands(VkSeekerService seekerService, VkIdFactory idFactory) {
    super("vk", seekerService, idFactory);
  }
}
